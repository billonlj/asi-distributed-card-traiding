import HTMLView from "../components/abstract/HTMLView.js";
import RoomList from "../components/roomList.js";
import CardList from "../components/cardList.js";
import InputCustom from "../components/input.js";

import roomService from "../services/roomService.js";
import CardService from "../services/cardService.js";

export default class RoomsView extends HTMLView {
    constructor() {
        super();
    }

    async render() {
        this.innerHTML = `
            <header-component></header-component>
            <div class="ui grid">
                <div class="ten wide column" id="rooms">
                </div>
                <button class="ui red button" onclick="${this.bind(() => this.toggleModal('show') ,"showModalAction")}">
                    Add room
                </button>
            </div>
            <div class="ui modal" id="modal-rooms">
                <i class="close icon"></i>
                <div class="header">
                    Room creation
                </div>
                <div class="content">
                    <div class="field">
                        <div id="roomNameContainer" class="ui left icon input">
                            <i class="user icon"></i>
                        </div>
                    </div>
                    <div class="ten wide column" id="list-cards">
                    </div>
                </div>
                <div class="actions">
                    <div class="ui black deny button" onclick="${this.bind(() => this.toggleModal('hide') ,"hideModalAction")}">
                        Cancel
                    </div>
                    <div class="ui positive right labeled icon button"  onclick="${this.bind(() => this.createRoom() ,"createRoomAction")}">
                        Create room
                        <i class="checkmark icon"></i>
                    </div>
                </div>
            </div>
        `;

        const rooms = await roomService.getAvailabeRooms();
        this.roomList = new RoomList(rooms, (room) => this.selectRoom(room));
        this.querySelector("#rooms").appendChild(this.roomList);

        this.cards = await CardService.getCardUser();
        this.selectedCard = null;
        this.cardsContainer = new CardList(this.cards, (card) => this.selectCard(card));
        this.querySelector("#list-cards").appendChild(this.cardsContainer);

        this.nameInput = new InputCustom("text", "name", "Room name");
        this.querySelector("#roomNameContainer").appendChild(this.nameInput);

        roomService.addAvailableRoomsEventListener("renderList", (rooms) => this.roomList.setRooms(rooms));
    }

    selectCard(card) {
        console.log(card)
        this.selectedCard = card;
    }

    selectRoom(room) {
        roomService.joinRoom(room);
        this.router.redirect("lobby");
    }

    toggleModal(status) {
        $('#modal-rooms').modal(status);
    }

    async createRoom() {
        console.log(this.selectedCard)

        const room = await roomService.createRoom({
            nameRoom: this.nameInput.getValue()
        });
        if(!room) return;

        console.log(room)
        this.selectRoom(room);
    }
}

customElements.define('rooms-view', RoomsView);