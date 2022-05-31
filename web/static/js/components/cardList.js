import HTMLBindableElement from "./abstract/HTMLBindableElement.js";

class CardList extends HTMLBindableElement {
	constructor(cards, callback, isSell = false) {
		super();

		this.cards = cards;
		this.oncardclick = callback;
		this.isSell = isSell;
	}

    onButtonClick(card) {
        this.oncardclick(card);
    }

	render() {
		this.innerHTML = `
            <div class="ten wide column">
                <h3 class="ui aligned header"> My Card List</h3>
                <!------------------------------------------------------------------------->
                <!--    ----------------------------- List ----------------------------- -->
                <!------------------------------------------------------------------------->
                <table class="ui fixed selectable single line celled table" id="cardListId">
                    <thead>
                        <tr>
                            <th class="three wide">Name</th>
                            <th class="three wide">Description</th>
                            <th>Family</th>
                            <th>HP</th>
                            <th>Energy</th>
                            <th>Defence</th>
                            <th>Attack</th>
                            ${!this.isSell ? '<th>Price</th>' : ''}
                            <th></th>
                        </tr>
                    </thead>
                    <tbody id="tableContent">
                        <template id="row">
                            <tr>
                                <td>
                                    <img class="ui avatar image" src={{img_src}}> <span>{{name}} </span>
                                </td>
                                <td>{{description}}</td>
                                <td>{{family_name}}</td>
                                <td>{{hp}}</td>
                                <td>{{energy}}</td>
                                <td>{{defense}}</td>
                                <td>{{attack}}</td>
                                ${!this.isSell ? '<td>{{price}}$</td>' : ''}
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

		this.cards.forEach(card => {
			const clone = document.importNode(template.content, true);
			const cardClone = { ...card };

            const cardDescription = card.cardInstance !== undefined ? card.cardInstance : card.card; 
            const newContent = clone.firstElementChild.innerHTML
                .replace(/{{family_src}}/g, card.family_src)
                .replace(/{{family_name}}/g, cardDescription.familyCard.nameFamily)
                .replace(/{{img_src}}/g, cardDescription.sourceUrlCard)
                .replace(/{{name}}/g, cardDescription.nameCard)
				.replace(/{{description}}/g, cardDescription.descriptionCard)
				.replace(/{{hp}}/g, cardDescription.hpCard)
				.replace(/{{energy}}/g, cardDescription.energyCard)
				.replace(/{{attack}}/g, cardDescription.attackCard)
				.replace(/{{defense}}/g, cardDescription.defenceCard)
				.replace(/{{price}}/g, card.priceSale)
			clone.firstElementChild.innerHTML = newContent;
			container.appendChild(clone);
			const trigger = container.lastElementChild.querySelector(".trigger");
			trigger.addEventListener('click', () => { this.onButtonClick(cardClone) });
		});
	}

	setCards(newCards) {
		this.cards = newCards;
		this.render();
	}
}

customElements.define('card-list', CardList);

export default CardList;    