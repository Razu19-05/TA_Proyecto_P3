using System.Net;
using System.Net.Http.Json;
using SimeSoft.Models.Alumnos;

namespace SimeSoft.Services.Api;

public class AlumnoApiService
{
    private readonly HttpClient _httpClient;

    public AlumnoApiService(IHttpClientFactory httpClientFactory)
    {
        _httpClient = httpClientFactory.CreateClient("SimeApi");
    }

    public async Task<AlumnoDto?> BuscarAsync(string criterio)
    {
        if (string.IsNullOrWhiteSpace(criterio))
            return null;

        string url = $"AlumnoRS/buscar?criterio={Uri.EscapeDataString(criterio)}";

        HttpResponseMessage response = await _httpClient.GetAsync(url);

        if (response.StatusCode == HttpStatusCode.NotFound)
            return null;

        if (!response.IsSuccessStatusCode)
            throw new Exception("No se pudo conectar con el servicio REST de alumnos.");

        return await response.Content.ReadFromJsonAsync<AlumnoDto>();
    }

    public async Task<bool> ExisteDniAsync(string dni)
    {
        if (string.IsNullOrWhiteSpace(dni))
            return false;

        string url = $"AlumnoRS/existe-dni/{Uri.EscapeDataString(dni)}";

        HttpResponseMessage response = await _httpClient.GetAsync(url);

        if (!response.IsSuccessStatusCode)
        {
            string error = await response.Content.ReadAsStringAsync();

            if (string.IsNullOrWhiteSpace(error))
            {
                error = "No se pudo validar si el alumno ya existe.";
            }

            throw new Exception(error);
        }

        return await response.Content.ReadFromJsonAsync<bool>();
    }

    // Actualiza los datos del alumno (PUT AlumnoRS/actualizar/{dni}).
    // El backend ubica al alumno por DNI y actualiza el resto de campos.
    public async Task<bool> ActualizarAsync(string dni, AlumnoDto alumno)
    {
        if (string.IsNullOrWhiteSpace(dni))
            return false;

        string url = $"AlumnoRS/actualizar/{Uri.EscapeDataString(dni)}";

        HttpResponseMessage response = await _httpClient.PutAsJsonAsync(url, alumno);

        return response.IsSuccessStatusCode;
    }
}