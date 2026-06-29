using System.Net.Http.Json;
using SimeSoft.Models.Aulas;

namespace SimeSoft.Services.Api;

public class AsignacionDocenteApiService
{
    private readonly HttpClient _httpClient;

    public AsignacionDocenteApiService(IHttpClientFactory httpClientFactory)
    {
        _httpClient = httpClientFactory.CreateClient("SimeApi");
    }

    public async Task<List<ProfesorAulaDto>> ListarPorAulaAsync(int idAula)
    {
        return await _httpClient.GetFromJsonAsync<List<ProfesorAulaDto>>(
            $"AsignacionDocenteRS/listarPorAula/{idAula}")
            ?? new List<ProfesorAulaDto>();
    }

    public async Task<List<ProfesorAulaDto>> ListarPorMatriculaCabeceraAsync(int idMatriculaCabecera)
    {
        return await _httpClient.GetFromJsonAsync<List<ProfesorAulaDto>>(
            $"AsignacionDocenteRS/listarPorMatriculaCabecera/{idMatriculaCabecera}")
            ?? new List<ProfesorAulaDto>();
    }

    public async Task<List<ProfesorAulaDto>> ListarProfesoresDisponiblesAsync(string criterio)
    {
        string url = $"AsignacionDocenteRS/profesoresDisponibles?criterio={Uri.EscapeDataString(criterio ?? "")}";

        return await _httpClient.GetFromJsonAsync<List<ProfesorAulaDto>>(url)
               ?? new List<ProfesorAulaDto>();
    }

    public async Task<bool> AsignarAsync(AsignacionDocenteRequestDto request)
    {
        HttpResponseMessage response =
            await _httpClient.PostAsJsonAsync("AsignacionDocenteRS/asignar", request);

        return response.IsSuccessStatusCode;
    }

    public async Task<bool> ActualizarAsync(int idAsignacionDocente, AsignacionDocenteRequestDto request)
    {
        HttpResponseMessage response =
            await _httpClient.PutAsJsonAsync(
                $"AsignacionDocenteRS/actualizar/{idAsignacionDocente}",
                request);

        return response.IsSuccessStatusCode;
    }

    public async Task<bool> EliminarAsync(int idAsignacionDocente)
    {
        HttpResponseMessage response =
            await _httpClient.DeleteAsync($"AsignacionDocenteRS/eliminar/{idAsignacionDocente}");

        return response.IsSuccessStatusCode;
    }
}