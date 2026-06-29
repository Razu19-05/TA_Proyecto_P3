using System.Net.Http.Json;
using System.Text.Json.Serialization;

namespace SimeSoft.Services.Api;

public class LoginApiService
{
    private readonly HttpClient _httpClient;

    public LoginApiService(IHttpClientFactory httpClientFactory)
    {
        _httpClient = httpClientFactory.CreateClient("SimeApi");
    }

    public async Task<LoginResponseDto> AutenticarAsync(string nombreUsuario, string contrasena)
    {
        LoginRequestDto request = new()
        {
            NombreUsuario = nombreUsuario,
            Contrasena = contrasena
        };

        HttpResponseMessage response = await _httpClient.PostAsJsonAsync("UsuarioRS/autenticar", request);

        if (!response.IsSuccessStatusCode)
        {
            string mensaje = await response.Content.ReadAsStringAsync();

            if (string.IsNullOrWhiteSpace(mensaje))
            {
                mensaje = "No se pudo iniciar sesión.";
            }

            mensaje = mensaje.Trim('"');

            throw new Exception(mensaje);
        }

        LoginResponseDto? usuario = await response.Content.ReadFromJsonAsync<LoginResponseDto>();

        if (usuario == null)
        {
            throw new Exception("No se pudo leer la respuesta del servicio REST.");
        }

        return usuario;
    }
}

public class LoginRequestDto
{
    [JsonPropertyName("nombreUsuario")]
    public string NombreUsuario { get; set; } = "";

    [JsonPropertyName("contrasena")]
    public string Contrasena { get; set; } = "";
}

public class LoginResponseDto
{
    [JsonPropertyName("idUsuario")]
    public int IdUsuario { get; set; }

    [JsonPropertyName("nombreUsuario")]
    public string NombreUsuario { get; set; } = "";

    [JsonPropertyName("correo")]
    public string Correo { get; set; } = "";

    [JsonPropertyName("rol")]
    public string Rol { get; set; } = "";
}