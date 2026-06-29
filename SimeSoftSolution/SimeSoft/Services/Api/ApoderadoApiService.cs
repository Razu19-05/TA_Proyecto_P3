using System.Net.Http.Json;

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
}