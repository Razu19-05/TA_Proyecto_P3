using System.Net.Http.Json;
using SimeSoft.Models.Matriculas;

namespace SimeSoft.Services.Api;

public class MatriculaApiService
{
    private readonly HttpClient _httpClient;

    public MatriculaApiService(IHttpClientFactory httpClientFactory)
    {
        _httpClient = httpClientFactory.CreateClient("SimeApi");
    }

    public async Task<List<VacanteMatriculaResponseDto>> BuscarVacantesAsync(
        string periodo,
        string nivel,
        string grado)
    {
        string url =
            $"MatriculaRS/vacantes?periodo={Uri.EscapeDataString(periodo)}" +
            $"&nivel={Uri.EscapeDataString(nivel)}" +
            $"&grado={Uri.EscapeDataString(grado)}";

        HttpResponseMessage response = await _httpClient.GetAsync(url);

        if (!response.IsSuccessStatusCode)
        {
            string error = await response.Content.ReadAsStringAsync();

            if (string.IsNullOrWhiteSpace(error))
            {
                error = "No se pudo consultar las vacantes.";
            }

            throw new Exception(error);
        }

        return await response.Content.ReadFromJsonAsync<List<VacanteMatriculaResponseDto>>()
               ?? new List<VacanteMatriculaResponseDto>();
    }

    public async Task<(bool Ok, MatriculaAlumnoNuevoResponseDto? Data, string Error)> MatricularAlumnoNuevoAsync(
        MatriculaAlumnoNuevoRequestDto request)
    {
        HttpResponseMessage response =
            await _httpClient.PostAsJsonAsync("MatriculaRS/alumnoNuevo", request);

        if (!response.IsSuccessStatusCode)
        {
            string error = await response.Content.ReadAsStringAsync();

            if (string.IsNullOrWhiteSpace(error))
            {
                error = "No se pudo registrar la matrícula del alumno nuevo.";
            }

            return (false, null, error);
        }

        MatriculaAlumnoNuevoResponseDto? data =
            await response.Content.ReadFromJsonAsync<MatriculaAlumnoNuevoResponseDto>();

        return (true, data, "");
    }
}
