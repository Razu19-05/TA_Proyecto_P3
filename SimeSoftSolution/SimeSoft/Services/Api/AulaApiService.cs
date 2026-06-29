using System.Net.Http.Json;
using SimeSoft.Models.Aulas;

namespace SimeSoft.Services.Api;

public class AulaApiService
{
    private readonly HttpClient _httpClient;

    public AulaApiService(IHttpClientFactory httpClientFactory)
    {
        _httpClient = httpClientFactory.CreateClient("SimeApi");
    }

    public async Task<List<AulaDto>> ListarAsync(
        string periodo,
        string nivel,
        string grado,
        string estado,
        string codigo)
    {
        string url =
            $"AulaRS/listar?periodo={Uri.EscapeDataString(periodo)}" +
            $"&nivel={Uri.EscapeDataString(nivel)}" +
            $"&grado={Uri.EscapeDataString(grado)}" +
            $"&estado={Uri.EscapeDataString(estado)}" +
            $"&codigo={Uri.EscapeDataString(codigo)}";

        return await _httpClient.GetFromJsonAsync<List<AulaDto>>(url)
               ?? new List<AulaDto>();
    }

    public async Task<AulaDto?> ObtenerDetalleAsync(int idAula)
    {
        return await _httpClient.GetFromJsonAsync<AulaDto>($"AulaRS/detalle/{idAula}");
    }

    public async Task<bool> CrearAsync(AulaGuardarDto aula)
    {
        HttpResponseMessage response =
            await _httpClient.PostAsJsonAsync("AulaRS/crear", aula);

        return response.IsSuccessStatusCode;
    }

    public async Task<bool> ActualizarAsync(AulaGuardarDto aula)
    {
        HttpResponseMessage response =
            await _httpClient.PutAsJsonAsync($"AulaRS/actualizar/{aula.IdAula}", aula);

        return response.IsSuccessStatusCode;
    }

    public async Task<bool> ActualizarCapacidadAsync(int idAula, int capacidad)
    {
        HttpResponseMessage response =
            await _httpClient.PutAsync($"AulaRS/capacidad/{idAula}?capacidad={capacidad}", null);

        return response.IsSuccessStatusCode;
    }

    public async Task<bool> EliminarAsync(int idAula)
    {
        HttpResponseMessage response =
            await _httpClient.DeleteAsync($"AulaRS/eliminar/{idAula}");

        return response.IsSuccessStatusCode;
    }
}