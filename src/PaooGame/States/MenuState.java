package PaooGame.States;

import PaooGame.RefLinks;
import PaooGame.UI.MenuGUI;

import java.awt.*;

/*! \class public class MenuState extends State
    \brief Implementeaza notiunea de menu pentru joc.
 */
public class MenuState extends State
{
    /*! \fn public MenuState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.


        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */

    private MenuGUI menuGUI;
    public MenuState(RefLinks refLink)
    {
            ///Apel al constructorului clasei de baza.
        super(refLink);
        menuGUI=new MenuGUI(refLink);

    }
    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniului.
     */
    @Override
    public void Update()
    {
        menuGUI.update();
    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a meniului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g)
    {
        menuGUI.draw(g);
    }
}