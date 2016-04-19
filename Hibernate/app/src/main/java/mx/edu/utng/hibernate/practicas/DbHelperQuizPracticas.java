package mx.edu.utng.hibernate.practicas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erika on 01/03/2016.
 */
public class DbHelperQuizPracticas extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "prácticas";
    // tasks table name
    private static final String TABLE_QUEST = "prácticas";
    // tasks Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_QUES = "question";
    private static final String KEY_ANSWER = "answer"; //correct option
    private static final String KEY_OPTA= "opta"; //option a
    private static final String KEY_OPTB= "optb"; //option b
    private static final String KEY_OPTC= "optc"; //option c
    private SQLiteDatabase dbase;
    public DbHelperQuizPracticas(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase=db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
                + " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
                +KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
        db.execSQL(sql);
        addQuestions();
        //db.close();
    }
    private void addQuestions()
    {
        PreguntaPracticas q1=new PreguntaPracticas("Que le hace falta al siguiente Codigo" +
                "<ul>\n" +
                "    <s:iterator value=\"parametros\" var=\"parametro\">\n" +
                "        <li><s:property value=\"#parametro.key\" />: <s:property value=\"#parametro.value[0]\" /></li>\n" +

                "</ul>" +
                "","</s:iterator>","</ul>","</li>","</s:iterator>");
        this.addQuestion(q1);
            PreguntaPracticas q2=new PreguntaPracticas("Como obtener una referencia al ValueStack","@Override\n" +
                    "public String execute() throws Exception\n" +
                    "{\n" +
                    "    ValueStack stack = ActionContext.getContext().getValueStack();\n", "@Override\n" +
                    "public String execute() throws Exception\n" +
                    "{\n" +
                    "    ValueStack stack = ActionContext.getContext().setValueStack();\n","@Override\n" +
                    "public String update() throws Exception\n" +
                    "{\n" +
                    "    ValueStack stack = ActionContext.getContext().getValueStack();\n", "@Override\n" +
                    "public String execute() throws Exception\n" +
                    "{\n" +
                    "    ValueStack stack = ActionContext.getContext().getValueStack();\n" );
        this.addQuestion(q2);
        PreguntaPracticas q3=new PreguntaPracticas("Es correcto el siguiente codigo <s:property value=\"mensaje\" />", "Si", "No", "Le falta un ValueStack","Si");
        this.addQuestion(q3);
        PreguntaPracticas q4=new PreguntaPracticas("Como hacer referencia a session desde raiz", "\n" +
                "#session", "\n" +
                "//session", "\n" +
                "@ session ", " \n" +
                "#session");
        this.addQuestion(q4);
        PreguntaPracticas q5=new PreguntaPracticas(" Hibernate 2 no nos da directamente una manera para invalidar la sesión de un usuario de nuestra aplicación web, así que para esto es necesario tener objeto al objeto \"HttpSession\", y para esto al objeto ","HttpServletRequest", "HttpServletNeccesary", "HttpServletRequestAll", "HttpServletRequest");
        this.addQuestion(q5);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
        // Create tables again
        onCreate(db);
    }
    // Adding new question
    public void addQuestion(PreguntaPracticas quest) {
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUES, quest.getQUESTION());
        values.put(KEY_ANSWER, quest.getANSWER());
        values.put(KEY_OPTA, quest.getOPTA());
        values.put(KEY_OPTB, quest.getOPTB());
        values.put(KEY_OPTC, quest.getOPTC());
        // Inserting Row
        dbase.insert(TABLE_QUEST, null, values);
    }
    public List<PreguntaPracticas> getAllQuestions() {
        List<PreguntaPracticas> quesListPracticas = new ArrayList<PreguntaPracticas>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
        dbase=this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PreguntaPracticas quest = new PreguntaPracticas();
                quest.setID(cursor.getInt(0));
                quest.setQUESTION(cursor.getString(1));
                quest.setANSWER(cursor.getString(2));
                quest.setOPTA(cursor.getString(3));
                quest.setOPTB(cursor.getString(4));
                quest.setOPTC(cursor.getString(5));
                quesListPracticas.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesListPracticas;
    }
    public int rowcount()
    {
        int row=0;
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        row=cursor.getCount();
        return row;
    }
}
