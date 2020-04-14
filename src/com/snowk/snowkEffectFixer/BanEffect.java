package com.snowk.snowkEffectFixer;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.GamePhase;
import com.snowk.snowkEffectFixer.ConfigLoader;


public class BanEffect extends JavaPlugin{
	
	public static BanEffect snowkPlugin;
	public ProtocolManager protocolManager;

    @Override
    public void onEnable() {
 
        if (!new File("./plugins/BanEffect/config.yml").exists()) {
            this.saveDefaultConfig();
            this.getLogger().info("BanEffect successfully created config file");
        }
        
    	snowkPlugin = this;
    	List<String> banList = ConfigLoader.bannedList;
    	
    	//pLib hello-world
    	protocolManager = ProtocolLibrary.getProtocolManager();
    	protocolManager.addPacketListener(new PacketAdapter(PacketAdapter.params()
    			.plugin(this)
    			.clientSide()
    			.serverSide()
    			.listenerPriority(ListenerPriority.NORMAL)
    			.gamePhase(GamePhase.PLAYING)
    			.optionAsync()
    			.options(ListenerOptions.SKIP_PLUGIN_VERIFIER)
    			.types(PacketType.Play.Server.ENTITY_EFFECT)
    	){
    	    @Override
    	    public void onPacketReceiving(PacketEvent e) {
    	    }
    	    @Override
    		public void onPacketSending(PacketEvent e) {
    	    	Player p = e.getPlayer();
    	    	Collection<PotionEffect> effectList = p.getActivePotionEffects();
    	    	for (String i : banList) {
    	  	    	if (effectList.toString().contains(i)) {
        	    		if (ConfigLoader.enableMsg) {
        	    			p.sendMessage(ConfigLoader.msg_cancel);
        	    		}
        	    		e.setCancelled(true);
        	    		return;
        	    	}
    	    	}
    	    	return;
    	    }
    	});
    	
/* Method 2 Alternative: how it different
 * */
//    	protocolManager.addPacketListener(new PacketAdapter(this,
//    	        ListenerPriority.NORMAL, 
//    	        PacketType.Play.Server.ENTITY_EFFECT) {
//    	    @Override
//    	    public void onPacketReceiving(PacketEvent e) {
//    	    }
//    	    @Override
//    		public void onPacketSending(PacketEvent e) {
//    	    	Player p = e.getPlayer();
//    	    	Collection<PotionEffect> effectList = p.getActivePotionEffects();
//    	    	for (String i : banList) {
//    	  	    	if (effectList.toString().contains(i)) {
//        	    		if (ConfigLoader.enableMsg) {
//        	    			p.sendMessage(ConfigLoader.msg_cancel);
//        	    		}
//        	    		e.setCancelled(true);
//        	    		return;
//        	    	}
//    	    	}
//    	    	return;
//    	    }
//    	});
    	getLogger().info("¡ì6===============================================");
    	getLogger().info("¡ìaBanEffect successfully enabled! - By:Bear");
    	getLogger().info("¡ìcSee more information at:¡ìe https://github.com/i493052739");
    	getLogger().info("¡ì6===============================================");
    }

    @Override
    public void onDisable() {
    	getLogger().info("BanEffect successfully disabled!");
    	
    }
   	
    public boolean onCommand(final CommandSender commandSender, final Command command, final String label, final String[] args) {
    	if (label.equalsIgnoreCase("BanEffect") || label.equalsIgnoreCase("be")) {
    		Player p = (Player)commandSender;
    		if (args.length == 0){
            	p.sendMessage("¡ì7¡ìl[¡ì3¡ìm======= ¡ìbBanEffect v1.0 ¡ì3¡ìm========¡ì7¡ìl]");
            	p.sendMessage("¡ìa/baneffect reload  ¡ì3- reload the plugin");
    			return true;
    		}
    		if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
            	p.sendMessage("¡ì7¡ìl[¡ì3¡ìm======= ¡ìbBanEffect v1.0 ¡ì3¡ìm========¡ì7¡ìl]");
            	p.sendMessage("¡ìa/baneffect reload  ¡ì3- reload the plugin");
    			return true;
    		}
    		if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                if (p.hasPermission("baneffect.admin.reload") || p.isOp()) {
                	this.reloadConfig();
                	ConfigLoader.bannedList = BanEffect.snowkPlugin.getConfig().getStringList("bannedEffect");
                	ConfigLoader.enableMsg = BanEffect.snowkPlugin.getConfig().getBoolean("enableMsg");
                	ConfigLoader.msg_cancel = BanEffect.snowkPlugin.getConfig().getString("Msg_Cancel").replace("&", "¡ì");
                	ConfigLoader.msg_reject = BanEffect.snowkPlugin.getConfig().getString("Msg_Reject").replace("&", "¡ì");
                	ConfigLoader.msg_reload = BanEffect.snowkPlugin.getConfig().getString("Msg_Reload").replace("&", "¡ì");
                	p.sendMessage(ConfigLoader.msg_reload);
                } else {
                	p.sendMessage(ConfigLoader.msg_reject);
                }
                return true;
            }
    	}
    	return false;
    }
}
