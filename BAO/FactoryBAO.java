package BAO;


public class FactoryBAO {
    public static AdministratorInterface getInstance(){
        return  new Administrator();
    }
}
