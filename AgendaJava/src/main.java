import view.TelaLogin;

public class main {
    public static void main(String[] args) {
        // Teste: verifica se o ícone está sendo encontrado no classpath
        System.out.println(main.class.getResource("/icons/save.png"));

        // Abre a tela de login
        new TelaLogin().setVisible(true);
    }
}
