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

    public async Task<(bool Ok, string Mensaje, string Error)> MatricularAlumnoExistenteAsync(
        SolicitudMatriculaRequestDto request)
    {
        HttpResponseMessage response =
            await _httpClient.PostAsJsonAsync("MatriculaRS/alumnoExistente", request);

        string contenido = await response.Content.ReadAsStringAsync();

        if (!response.IsSuccessStatusCode)
        {
            string error = string.IsNullOrWhiteSpace(contenido)
                ? "No se pudo registrar la matrícula del alumno regular."
                : contenido;

            return (false, "", error);
        }

        return (true, contenido, "");
    }

    // Lista los pagos del alumno (GET PagoRS/listar_pagos_alumno/{idAlumno}).
    // Se usa para verificar deudas (pagos en estado PENDIENTE) antes de matricular.
    public async Task<(bool Ok, List<PagoAlumnoDto> Pagos, string Error)> ListarPagosAlumnoAsync(int idAlumno)
    {
        HttpResponseMessage response =
            await _httpClient.GetAsync($"PagoRS/listar_pagos_pendientes_alumno/{idAlumno}");

        if (!response.IsSuccessStatusCode)
        {
            string error = await response.Content.ReadAsStringAsync();

            if (string.IsNullOrWhiteSpace(error))
            {
                error = "No se pudieron cargar los pagos del alumno.";
            }

            return (false, new(), error);
        }

        List<PagoAlumnoDto>? pagos =
            await response.Content.ReadFromJsonAsync<List<PagoAlumnoDto>>();

        return (true, pagos ?? new(), "");
    }

    // Busca cualquier persona por DNI (trabajador o externo ya registrado).
    // Devuelve null si no existe (HTTP 404) -> el formulario se llena a mano.
    public async Task<PersonaRequestDto?> BuscarPersonaPorDniAsync(string dni)
    {
        HttpResponseMessage response =
            await _httpClient.GetAsync($"EmpleadosRS/buscarPersona/{dni}");

        if (!response.IsSuccessStatusCode)
        {
            return null;
        }

        if (response.Content.Headers.ContentLength == 0)
        {
            return null;
        }

        return await response.Content.ReadFromJsonAsync<PersonaRequestDto>();
    }
}
