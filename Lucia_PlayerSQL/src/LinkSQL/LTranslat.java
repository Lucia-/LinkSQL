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
			a = "保存在線玩家結束";
			b = "開始在線玩家結束: ";
			c = " 人";
			d = "保存玩家 ";
			e = "載入玩家 ";
			f = " 成功";
			g = " 失敗";
			h = "你沒有 linksql.admin 權限";
			i = "數據表連接成功";
			j = "數據表驗證成功";
			k = "";
			l = "";
			m = "數據表連接失敗";
			n = "請再配置表配置好SQL後啟用該插件";
			o = "進度: ";
		}
	}
}
