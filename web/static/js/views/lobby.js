import HTMLView from "../components/abstract/HTMLView.js";
import Card from "../components/card.js"

import roomService from "../services/roomService.js";
import userService from "../services/userService.js";

export default class LobbyView extends HTMLView {
    constructor() {
        super();
    }

    async render() {
        this.innerHTML = `
            <header-component></header-component>
            <div class="ui grid">
                <div class="ui segment" id="lobby-loader">
                    <div class="ui active dimmer">
                        <div class="ui text loader">Waiting for the game to start...</div>
                    </div>
                    <p></p>
                </div>
                <div class="ten wide column" id="eventRoom">
                </div>
            </div>
        `;

        this.loader = document.querySelector("#lobby-loader");
        this.eventRoom = this.querySelector("#eventRoom");

        this.updateStatus(false);


        this.myCard = new Card(undefined, true);
        this.eventRoom.appendChild(this.myCard);
        this.enemyCard = new Card(undefined, true);
        this.eventRoom.appendChild(this.enemyCard);

        roomService.addCurrentRoomEventListener("updateLobby", (room) => {
            this.eventRoom.innerHTML = `GameStatus ${room.status}`;

            this.updateStatus(room.status == "STARTED");
            this.updateCards(room);
        });
    }

    updateCards(room) {
        console.log(room)
        console.log("My card: ", room.players[userService.get_user_id()]);
        this.myCard.setCard(room.players[userService.get_user_id()]);
    }

    updateStatus(status = false) {
        this.hasStarted = status;
        
        if(this.hasStarted) {
            this.loader.classList.add("hidden");
            this.eventRoom.classList.remove("hidden");
        } else {
            this.loader.classList.remove("hidden");
            this.eventRoom.classList.add("hidden");
        }
    }
}

customElements.define('lobby-view', LobbyView);