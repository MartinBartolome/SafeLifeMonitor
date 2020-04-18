package schutzengel.com.safelifemonitor;

import java.util.ArrayList;

public class Datenbank  {
    private static Datenbank instance = null;
    private ApplikationEinstellungen applikationEinstellungen = null;

    public static Datenbank getInstance() {
        if (null == instance) {
            instance = new Datenbank();
        }
        return instance;
    }

    private  Datenbank() {
        this.applikationEinstellungen = new ApplikationEinstellungen();
    }

    public ArrayList<NotfallKontakt> getNotfallKontakte() {
        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append("SELECT * FROM ");
        sqlCommand.append(NotfallKontakt.getTableName());
       // this.driver.runQuery(sqlCommand.toString());
//// To do
        return null;
    }

    public ApplikationEinstellungen getApplikationEinstellungen() {
        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append("SELECT * FROM ");
        sqlCommand.append(ApplikationEinstellungen.getTableName());
      //  this.driver.runQuery(sqlCommand.toString());
        // To do...
        return this.applikationEinstellungen;
    }

    public NotfallKontakt getNotfallKontakt(String telefonNummer)
    {
        // To do...
        return null;
    }

    public void set(NotfallKontakt notfallKontakt) {

    }

    public void set(ArrayList<NotfallKontakt> notfallKontakte) {
        StringBuilder sqlCommand = new StringBuilder();
        // Delete all rows
        sqlCommand.append("DELETE FROM ");
        sqlCommand.append(NotfallKontakt.getTableName());
//        this.driver.runCommand(sqlCommand.toString());
        // Insert rows
        sqlCommand = new StringBuilder();
        sqlCommand.append("INSERT INTO ");
        sqlCommand.append(NotfallKontakt.getTableName());
        sqlCommand.append(" VALUES ");
        boolean firstRow = true;
        for (NotfallKontakt notfallKontakt : notfallKontakte) {
            if (!firstRow) {
                sqlCommand.append(",");
            }
            firstRow = false;
            sqlCommand.append("(");
            sqlCommand.append(notfallKontakt.getPrioritaet().ordinal());
            sqlCommand.append(",");
            sqlCommand.append(notfallKontakt.getIcon().ordinal());
            sqlCommand.append(",");
            sqlCommand.append(notfallKontakt.getBeschreibung());
            sqlCommand.append(",");
            sqlCommand.append(notfallKontakt.getAlarmTelefonNummer());
            sqlCommand.append(")");
        }
//        this.driver.runCommand(sqlCommand.toString());
    }

    public void set(ApplikationEinstellungen applikationEinstellungen) {
        StringBuilder sqlCommand = new StringBuilder();
        // Delete all rows
        sqlCommand.append("DELETE FROM ");
        sqlCommand.append(ApplikationEinstellungen.getTableName());
//        this.driver.runCommand(sqlCommand.toString());
        // Insert rows
        sqlCommand = new StringBuilder();
        sqlCommand.append("INSERT INTO ");
        sqlCommand.append(ApplikationEinstellungen.getTableName());
        sqlCommand.append(" VALUES ");
        // To do
  //      this.driver.runCommand(sqlCommand.toString());
    }
}

