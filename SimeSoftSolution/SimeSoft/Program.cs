using SimeSoft.Components;
using SimeSoft.Components.State;
using SimeSoft.Services.Api;
var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddRazorComponents()
    .AddInteractiveServerComponents();

builder.Services.AddScoped<MenuState>();
builder.Services.AddScoped<PagoState>();
builder.Services.AddScoped<AulaState>();
builder.Services.AddScoped<AlumnoState>();
builder.Services.AddScoped<MatriculaState>();

builder.Services.AddHttpClient("SimeApi", client =>
{
    client.BaseAddress = new Uri("http://localhost:8080/WebApplication/webresources/");
});

builder.Services.AddScoped<PersonalApiService>();
var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error", createScopeForErrors: true);
    // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
    app.UseHsts();
}
app.UseStatusCodePagesWithReExecute("/not-found", createScopeForStatusCodePages: true);
app.UseHttpsRedirection();

app.UseAntiforgery();

app.MapStaticAssets();
app.MapRazorComponents<App>()
    .AddInteractiveServerRenderMode();

app.Run();
