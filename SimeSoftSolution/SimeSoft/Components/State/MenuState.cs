namespace SimeSoft.Components.State;
public class MenuState
{
    public bool MenuAbierto { get; private set; } = true;

    public void CambiarMenu()
    {
        MenuAbierto = !MenuAbierto;
    }
}
