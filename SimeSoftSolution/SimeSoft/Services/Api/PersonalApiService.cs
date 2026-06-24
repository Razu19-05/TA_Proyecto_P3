using System.Net.Http.Json;
using SimeSoft.Models.Personal;

namespace SimeSoft.Services.Api;

public class PersonalApiService
{
    private readonly HttpClient _httpClient;

    public PersonalApiService(IHttpClientFactory httpClientFactory)
    {
        _httpClient = httpClientFactory.CreateClient("SimeApi");
    }

    public async Task<List<PersonalDto>> ListarAsync()
    {
        return await _httpClient.GetFromJsonAsync<List<PersonalDto>>("EmpleadosRS/listar")
               ?? new List<PersonalDto>();
    }

    public async Task<PersonalDto?> CrearAsync(PersonalDto personal)
    {
        HttpResponseMessage response = await _httpClient.PostAsJsonAsync("EmpleadosRS/crear", personal);

        if (!response.IsSuccessStatusCode)
            return null;

        if (response.Content.Headers.ContentLength == 0)
            return personal;

        return await response.Content.ReadFromJsonAsync<PersonalDto>() ?? personal;
    }

    public async Task<PersonalDto?> ActualizarAsync(PersonalDto personal)
    {
        HttpResponseMessage response =
            await _httpClient.PutAsJsonAsync($"EmpleadosRS/actualizar/{personal.IdPersona}", personal);

        if (!response.IsSuccessStatusCode)
            return null;

        if (response.Content.Headers.ContentLength == 0)
            return personal;

        return await response.Content.ReadFromJsonAsync<PersonalDto>() ?? personal;
    }

    public async Task<bool> EliminarAsync(int idPersona)
    {
        HttpResponseMessage response =
            await _httpClient.DeleteAsync($"EmpleadosRS/eliminar/{idPersona}");

        return response.IsSuccessStatusCode;
    }
}
