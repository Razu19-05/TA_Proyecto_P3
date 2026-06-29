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

    // Lista TODOS los pagos del alumno (pagados, pendientes, anulados) para la
    // ficha / situación económica (GET PagoRS/listar_pagos_alumno/{idAlumno}).
    public async Task<List<PagoAlumnoDto>> ListarPagosCompletosAlumnoAsync(int idAlumno)
    {
        HttpResponseMessage response =
            await _httpClient.GetAsync($"PagoRS/listar_pagos_alumno/{idAlumno}");

        if (!response.IsSuccessStatusCode)
        {
            return new();
        }

        List<PagoAlumnoDto>? pagos =
            await response.Content.ReadFromJsonAsync<List<PagoAlumnoDto>>();

        return pagos ?? new();
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

    // Lista los apoderados (relaciones familiares activas) ya registrados de un
    // alumno a partir de su DNI (GET ApoderadosRS/listar/{dni}).
    // Devuelve lista vacía si el alumno no tiene apoderados o si el servicio falla.
    public async Task<List<RelacionFamiliarDto>> ListarApoderadosAsync(string dni)
    {
        if (string.IsNullOrWhiteSpace(dni))
        {
            return new();
        }

        HttpResponseMessage response =
            await _httpClient.GetAsync($"ApoderadosRS/listar/{Uri.EscapeDataString(dni.Trim())}");

        if (!response.IsSuccessStatusCode)
        {
            return new();
        }

        if (response.Content.Headers.ContentLength == 0)
        {
            return new();
        }

        List<RelacionFamiliarDto>? apoderados =
            await response.Content.ReadFromJsonAsync<List<RelacionFamiliarDto>>();

        return apoderados ?? new();
    }
}
