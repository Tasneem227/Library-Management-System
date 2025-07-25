package DAO;
public class FactoryDAO {
    public static LibraryInterface getInstance(){
        return Library.singleInstance();
    }
}