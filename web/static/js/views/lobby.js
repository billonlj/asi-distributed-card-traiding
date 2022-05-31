import HTMLView from "../components/abstract/HTMLView.js";

import roomService from "../services/roomService.js";

export default class LobbyView extends HTMLView {
    constructor() {
        super();
    }

    async render() {
        this.innerHTML = `
            <header-component></header-component>
            <div class="ui grid">
                <div class="ten wide column" id="eventRoom">
                </div>
            </div>
        `;

        this.eventRoom = this.querySelector("#eventRoom");
        roomService.addCurrentRoomEventListener("updateLobby", (event) => {
            const label = event.numberOfPlayers > 1 
                ? 'players' 
                : 'player';
            
            this.eventRoom.innerHTML = `${event.numberOfPlayers} ${label}`;
        })
    }
}

customElements.define('lobby-view', LobbyView);