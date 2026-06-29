using System.Net.Http.Json;
using SimeSoft.Models.Matriculas;

namespace SimeSoft.Services.Api;

public class ApoderadoApiService
{
    private readonly HttpClient _httpClient;

    public ApoderadoApiService(IHttpClientFactory httpClientFactory)
    {
        _httpClient = httpClientFactory.CreateClient("SimeApi");
    }

    public async Task<bool> ExisteDniAsync(string dni)
    {
        if (string.IsNullOrWhiteSpace(dni))
            return false;

        string url = $"ApoderadosRS/existe-dni/{Uri.EscapeDataString(dni)}";

        HttpResponseMessage response = await _httpClient.GetAsync(url);

        if (!response.IsSuccessStatusCode)
        {
            string error = await response.Content.ReadAsStringAsync();

            if (string.IsNullOrWhiteSpace(error))
            {
                error = "No se pudo verificar el DNI del apoderado.";
            }

            throw new Exception(error);
        }

        return await response.Content.ReadFromJsonAsync<bool>();
    }

    // Agrega un apoderado a un alumno (POST ApoderadosRS/agregar/{dni}).
    // Devuelve (ok, error). El backend crea la persona si no existe y la relación familiar.
    public async Task<(bool Ok, string Error)> AgregarAsync(string dniAlumno, ApoderadoNuevoRequestDto apoderado)
    {
        string url = $"ApoderadosRS/agregar/{Uri.EscapeDataString(dniAlumno)}";

        HttpResponseMessage response = await _httpClient.PostAsJsonAsync(url, apoderado);

        if (!response.IsSuccessStatusCode)
        {
            string error = await response.Content.ReadAsStringAsync();
            return (false, string.IsNullOrWhiteSpace(error) ? "No se pudo agregar el apoderado." : error);
        }

        return (true, "");
    }

    // Edita un apoderado existente (PUT ApoderadosRS/actualizar/{idRelacionFamiliar}).
    public async Task<(bool Ok, string Error)> ActualizarAsync(int idRelacionFamiliar, ApoderadoNuevoRequestDto apoderado)
    {
        string url = $"ApoderadosRS/actualizar/{idRelacionFamiliar}";

        HttpResponseMessage response = await _httpClient.PutAsJsonAsync(url, apoderado);

        if (!response.IsSuccessStatusCode)
        {
            string error = await response.Content.ReadAsStringAsync();
            return (false, string.IsNullOrWhiteSpace(error) ? "No se pudo actualizar el apoderado." : error);
        }

        return (true, "");
    }

    // Elimina (baja lógica) un apoderado (DELETE ApoderadosRS/eliminar/{idRelacionFamiliar}).
    public async Task<(bool Ok, string Error)> EliminarAsync(int idRelacionFamiliar)
    {
        string url = $"ApoderadosRS/eliminar/{idRelacionFamiliar}";

        HttpResponseMessage response = await _httpClient.DeleteAsync(url);

        if (!response.IsSuccessStatusCode)
        {
            string error = await response.Content.ReadAsStringAsync();
            return (false, string.IsNullOrWhiteSpace(error) ? "No se pudo eliminar el apoderado." : error);
        }

        return (true, "");
    }
}