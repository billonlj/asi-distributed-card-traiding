import HTMLView from "../components/abstract/HTMLView.js";
import RoomList from "../components/roomList.js";
import CardList from "../components/cardList.js";
import InputCustom from "../components/input.js";

import roomService from "../services/roomService.js";
import CardService from "../services/cardService.js";

export default class RoomsView extends HTMLView {
    constructor() {
        super();

        this.selectedRoom = undefined;
    }

    async render() {
        this.innerHTML = `
            <header-component></header-component>
            <div class="ui grid">
                <div class="ten wide column" id="rooms">
                </div>
                <button class="ui red button" onclick="${this.bind(() => this.toggleModalCreate('show') ,"showCreateModalAction")}">
                    Add room
                </button>
            </div>



            <div class="ui modal" id="create-modal-rooms">
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
                </div>
                <div class="actions">
                    <div class="ui black deny button" onclick="${this.bind(() => this.toggleModalCreate('hide') ,"hideCreateModalAction")}">
                        Cancel
                    </div>
                    <div class="ui positive right labeled icon button"  onclick="${this.bind(() => this.createRoom() ,"createRoomAction")}">
                        <span id="modal-display">Create a room</span>
                        <i class="checkmark icon"></i>
                    </div>
                </div>
            </div>




            <div class="ui modal" id="join-modal-rooms">
                <i class="close icon"></i>
                <div class="header">
                    Join the room: <span id="roomLabel"></span>
                </div>
                <div class="content">
                    <div class="ten wide column" id="list-cards">
                    </div>
                </div>
                <div class="actions">
                    <div class="ui black deny button" onclick="${this.bind(() => this.toggleModalJoin('hide') , "hideJoinModalAction")}">
                        Cancel
                    </div>
                    <div class="ui positive right labeled icon button"  onclick="${this.bind(() => this.joinRoom() ,"joinRoomAction")}">
                        <span id="modal-display">Join the room</span>
                        <i class="checkmark icon"></i>
                    </div>
                </div>
            </div>
        `;

        const rooms = await roomService.getAvailabeRooms();
        this.roomList = new RoomList(rooms, (room) => this.selectRoom(room));
        this.querySelector("#rooms").appendChild(this.roomList);

        this.cards = await CardService.getCardUser();
        this.cardsContainer = new CardList(this.cards, (card) => this.selectCard(card), true);
        this.querySelector("#list-cards").appendChild(this.cardsContainer);

        this.nameInput = new InputCustom("text", "name", "Room name");
        this.querySelector("#roomNameContainer").appendChild(this.nameInput);

        this.roomLabel = document.querySelector("#roomLabel");

        roomService.addAvailableRoomsEventListener("renderList", (rooms) => this.roomList.setRooms(rooms));
    }

    selectCard(card) {
        this.selectedCard = card;
    }

    async selectRoom(room) {
        this.selectedRoom = room;
        this.roomLabel.innerHTML = room?.nameRoom;
        this.toggleModalJoin('show');
    }

    toggleModalCreate(status) {
        $('#create-modal-rooms').modal(status);
    }

    toggleModalJoin(status) {
        $('#join-modal-rooms').modal(status);
    }

    async createRoom() {
        const room = await roomService.createRoom({
            nameRoom: this.nameInput.getValue()
        });
        if(!room) return;

        this.toggleModalCreate('hide');
        
        this.selectedRoom = room;
        this.selectRoom(room);
    }

    async joinRoom() {
        if(!this.selectedRoom) {
            alert("Vous n'avez pas sélectionné de room !");
        }

        if(!this.selectedCard) {
            alert("Vous n'avez pas sélectionné de carte !");
        }

        const joinRoomDto = {
            idRoom: this.selectedRoom.idRoom,
            idCardInstance: this.selectedCard.idInstance,
        }

        await roomService.joinRoom(joinRoomDto);
        this.router.redirect("lobby");
    }
}

customElements.define('rooms-view', RoomsView);