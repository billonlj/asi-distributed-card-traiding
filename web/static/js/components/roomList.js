import HTMLBindableElement from "./abstract/HTMLBindableElement.js";

class RoomList extends HTMLBindableElement {
	constructor(rooms, callback) {
		super();

		this.rooms = rooms;
		this.onRoomClick = callback;
	}

    onButtonClick(room) {
        this.onRoomClick(room);
    }

	render() {
		this.innerHTML = `
            <div class="ten wide column">
                <h3 class="ui aligned header">Available rooms</h3>
                <!------------------------------------------------------------------------->
                <!--    ----------------------------- List ----------------------------- -->
                <!------------------------------------------------------------------------->
                <table class="ui fixed selectable single line celled table" id="cardListId">
                    <thead>
                        <tr>
                            <th class="three wide">Id</th>
                            <th class="three wide">Name</th>
                            <th class="three wide">Status</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody id="tableContent">
                        <template id="row">
                            <tr>
                                <td>{{id}}</td>
                                <td>{{name}}</td>
                                <td>{{status}}</td>
                                <td>
                                    <div class="ui vertical animated button trigger" tabindex="0">
                                        <div class="hidden content">Sell</div>
                                        <div class="visible content">
                                            <i class="shop icon"></i>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </template>
                    </tbody>
                </table>
            </div>
        `;

		const container = this.querySelector('#tableContent');
		const template = this.querySelector('#row');

		this.rooms.forEach(room => {
			const clone = document.importNode(template.content, true);
			const roomClone = { ...room };

            const newContent = clone.firstElementChild.innerHTML
                .replace(/{{id}}/g, roomClone.idRoom)
                .replace(/{{name}}/g, roomClone.nameRoom)
                .replace(/{{status}}/g, roomClone.status)
			clone.firstElementChild.innerHTML = newContent;
			container.appendChild(clone);

			const trigger = container.lastElementChild.querySelector(".trigger");
			trigger.addEventListener('click', () => { this.onButtonClick(roomClone) });
		});
	}

	setRooms(newRooms) {
		this.rooms = newRooms;
        console.log(newRooms)
		this.render();
	}
}

customElements.define('room-list', RoomList);

export default RoomList;    