package LinkSQL;

public class LTranslat
{
	static String a;
	static String b;
	static String c;
	static String d;
	static String e;
	static String f;
	static String g;
	static String o;
	static String h;
	static String i;
	static String j;
	static String k;
	static String l;
	static String m;
	static String n;

	static void translat()
	{
		if (LPlayerSQL.plugin.getConfig().getBoolean("english")) {
			a = "Save all online players complate";
			b = "Online: ";
			c = " Players";
			d = "Save player ";
			e = "Load player ";
			f = " success";
			g = " failed";
			h = "You don't hava linksql.admin permission";
			i = "Connect success";
			j = "Check success";
			k = "";
			l = "";
			m = "Connect failed";
			n = "Turn on the plugin in config.yml";
			o = "Total: ";
		}
		else {
			a = "�O�s�b�u���a����";
			b = "�}�l�b�u���a����: ";
			c = " �H";
			d = "�O�s���a ";
			e = "���J���a ";
			f = " ���\";
			g = " ����";
			h = "�A�S�� linksql.admin �v��";
			i = "�ƾڪ�s�����\";
			j = "�ƾڪ����Ҧ��\";
			k = "";
			l = "";
			m = "�ƾڪ�s������";
			n = "�ЦA�t�m��t�m�nSQL��ҥθӴ���";
			o = "�i��: ";
		}
	}
}
