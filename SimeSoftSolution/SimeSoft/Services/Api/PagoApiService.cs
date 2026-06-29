using System.Net.Http.Json;
using SimeSoft.Models.Matriculas;

namespace SimeSoft.Services.Api;

public class PagoApiService
{
    private readonly HttpClient _httpClient;

    public PagoApiService(IHttpClientFactory httpClientFactory)
    {
        _httpClient = httpClientFactory.CreateClient("SimeApi");
    }

    public async Task<List<PagoAlumnoDto>> ListarPagosAlumnoAsync(int idAlumno)
    {
        return await ListarPagosAsync($"PagoRS/listar_pagos_alumno/{idAlumno}");
    }

    public async Task<List<PagoAlumnoDto>> ListarPagosPendientesAlumnoAsync(int idAlumno)
    {
        return await ListarPagosAsync($"PagoRS/listar_pagos_pendientes_alumno/{idAlumno}");
    }

    public async Task<PagoAlumnoDto> PagarPagoAsync(int idPago, string observacion)
    {
        CambioEstadoPagoRequestDto request = new()
        {
            Observacion = observacion
        };

        return await CambiarEstadoPagoAsync($"PagoRS/pagar/{idPago}", request);
    }

    public async Task<PagoAlumnoDto> AnularPagoAsync(int idPago, string observacion)
    {
        CambioEstadoPagoRequestDto request = new()
        {
            Observacion = observacion
        };

        return await CambiarEstadoPagoAsync($"PagoRS/anular/{idPago}", request);
    }

    private async Task<List<PagoAlumnoDto>> ListarPagosAsync(string url)
    {
        HttpResponseMessage response = await _httpClient.GetAsync(url);

        if (!response.IsSuccessStatusCode)
        {
            string error = await response.Content.ReadAsStringAsync();

            if (string.IsNullOrWhiteSpace(error))
            {
                error = "No se pudieron cargar los pagos del alumno.";
            }

            throw new Exception(error);
        }

        List<PagoAlumnoDto>? pagos =
            await response.Content.ReadFromJsonAsync<List<PagoAlumnoDto>>();

        return pagos ?? new List<PagoAlumnoDto>();
    }

    private async Task<PagoAlumnoDto> CambiarEstadoPagoAsync(
        string url,
        CambioEstadoPagoRequestDto request)
    {
        HttpResponseMessage response = await _httpClient.PutAsJsonAsync(url, request);

        if (!response.IsSuccessStatusCode)
        {
            string error = await response.Content.ReadAsStringAsync();

            if (string.IsNullOrWhiteSpace(error))
            {
                error = "No se pudo actualizar el estado del pago.";
            }

            throw new Exception(error);
        }

        PagoAlumnoDto? pagoActualizado =
            await response.Content.ReadFromJsonAsync<PagoAlumnoDto>();

        if (pagoActualizado is null)
        {
            throw new Exception("No se pudo leer la respuesta del servicio REST.");
        }

        return pagoActualizado;
    }
}

public class CambioEstadoPagoRequestDto
{
    public string Observacion { get; set; } = "";
}