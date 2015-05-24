package appwarp;

import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpReasonCode;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;

public class ConnectionListener implements ConnectionRequestListener {

	WarpController callBack;
	
	public ConnectionListener(WarpController callBack){
		this.callBack = callBack;
	}
	
	public void onConnectDone(ConnectEvent e) {
		if(e.getResult()==WarpResponseResultCode.SUCCESS){
			callBack.onConnectDone(true);
		}else{
			switch (e.getResult()) {
				case WarpResponseResultCode.AUTH_ERROR:
                    System.out.println("Auth Error");
					break;
				case WarpResponseResultCode.CONNECTION_ERROR:
                    System.out.println("Connection Error");
					break;
				case WarpResponseResultCode.UNKNOWN_ERROR:
                    System.out.println("Unknown Error");
					break;
                default:
                    System.out.println(e.getResult());
                    break;
			}
			callBack.onConnectDone(false);
		}
	}

	public void onDisconnectDone(ConnectEvent e) {
		
	}

	@Override
	public void onInitUDPDone (byte result) {
		if(result==WarpResponseResultCode.SUCCESS){
			callBack.isUDPEnabled = true;
		}
	}

}
