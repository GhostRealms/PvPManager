package me.NoChance.PvPManager.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Properties;
import me.NoChance.PvPManager.PvPManager;
import org.bukkit.ChatColor;

public class Messages {

	public PvPManager plugin;
	public Properties lang = new Properties();
	public final File messagesFile;

	public static String Already_Disabled;
	public static String Already_Enabled;
	public static String Other_Status_Enabled;
	public static String Others_Status_Disabled;
	public static String PvP_Disabled;
	public static String PvP_Enabled;
	public static String Self_Status_Disabled;
	public static String Self_Status_Enabled;
	public static String Command_Denied_InCombat;
	public static String Attack_Denied_You;
	public static String Attack_Denied_Other;
	public static String You_Are_InCombat;
	public static String Out_Of_Combat;
	public static String PvP_On;
	public static String PvP_Off;
	public static String Newbie_Protection;

	public Messages(PvPManager plugin) {
		this.plugin = plugin;
		this.messagesFile = new File(plugin.getDataFolder(), "messages.properties");
		if (plugin.getConfig().getInt("Config Version") < 6) {
			resetMessages();
		}
		load();
	}

	public void load() {

		if (!messagesFile.exists()) {
			plugin.saveResource("messages.properties", false);
			plugin.getLogger().info("New Messages File Created Successfully!");
		}
		try {
			if (messagesFile.exists()) {
				FileInputStream in = new FileInputStream(messagesFile);
				lang.load(in);
				checkChanges();
				getMessages();
				in.close();
			}
		} catch (IOException e) {
		}
	}

	public String getString(String key) {
		return ChatColor.translateAlternateColorCodes('&', lang.getProperty(key));
	}

	public void getMessages() {
		Already_Disabled = getString("Already_Disabled");
		Already_Enabled = getString("Already_Enabled");
		Other_Status_Enabled = getString("Other_Status_Enabled");
		Others_Status_Disabled = getString("Others_Status_Disabled");
		PvP_Disabled = getString("PvP_Disabled");
		PvP_Enabled = getString("PvP_Enabled");
		Self_Status_Disabled = getString("Self_Status_Disabled");
		Self_Status_Enabled = getString("Self_Status_Enabled");
		Command_Denied_InCombat = getString("Command_Denied_InCombat");
		Attack_Denied_You = getString("Attack_Denied_You");
		Attack_Denied_Other = getString("Attack_Denied_Other");
		You_Are_InCombat = getString("You_Are_InCombat");
		Out_Of_Combat = getString("Out_Of_Combat");
		PvP_On = getString("PvP_On");
		PvP_Off = getString("PvP_Off");
		Newbie_Protection = getString("Newbie_Protection");
	}

	public void checkChanges() {
		Properties original = new Properties();
		try {
			original.load(plugin.getResource("messages.properties"));
			Enumeration<Object> originalKeys = original.keys();
			while (originalKeys.hasMoreElements()) {
				String a = (String) originalKeys.nextElement();
				if (!lang.containsKey(a)) {
					addMessage(a + " = " + original.getProperty(a));
					lang.setProperty(a, original.getProperty(a));
				}
			}
		} catch (IOException e) {
		}
	}

	public void addMessage(String a) {
		try {
			FileWriter fw = new FileWriter(messagesFile, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(a);
			pw.close();
			fw.close();
		} catch (IOException e) {
		}
	}

	public void resetMessages() {
		if (messagesFile.exists()) {
			messagesFile.delete();
		}
	}
}