package appwarp;

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LobbyEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.LobbyRequestListener;

public class LobbyListener implements LobbyRequestListener {
    private WarpController callBack;

    public LobbyListener(WarpController callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onJoinLobbyDone(LobbyEvent lobbyEvent) {
        callBack.onJoinLobbyDone(lobbyEvent);
    }

    @Override
    public void onLeaveLobbyDone(LobbyEvent lobbyEvent) {

    }

    @Override
    public void onSubscribeLobbyDone(LobbyEvent lobbyEvent) {
        if(lobbyEvent.getResult() == WarpResponseResultCode.SUCCESS) {
            callBack.setJoinedLobby(true);
        }
        else {
            callBack.setJoinedLobby(false);
        }
    }

    @Override
    public void onUnSubscribeLobbyDone(LobbyEvent lobbyEvent) {

    }

    @Override
    public void onGetLiveLobbyInfoDone(LiveRoomInfoEvent liveRoomInfoEvent) {
        if(liveRoomInfoEvent.getResult()== WarpResponseResultCode.SUCCESS){
            //callBack.onGetLiveRoomInfo(liveRoomInfoEvent.getJoinedUsers());
        }else{
            //callBack.onGetLiveRoomInfo(null);
        }
    }
}
