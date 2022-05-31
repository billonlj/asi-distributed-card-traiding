import HTMLView from "../components/abstract/HTMLView.js";
import CardList from "../components/cardList.js"
import Card from "../components/card.js"

import SaleService from "../services/saleService.js";
import CardService from "../services/cardService.js";

export default class SellView extends HTMLView {
    constructor() {
        super();
    }

    async render() {
        this.cards = await CardService.getCardUser();

        this.innerHTML = `
            <header-component></header-component>
            <div class="ui grid">
                <div class="ten wide column" id="cards">
                </div>
                <div class=" five wide column" id="selectedCard">
                </div>
                <span id="error-price"></span>
            </div>
        `;
        
        this.cardsContainer = new CardList(this.cards, (card) => this.selectCard(card), true);
        this.errorSpan = document.querySelector("#error-price")
        this.querySelector("#cards").appendChild(this.cardsContainer);
        if ( this.cards.length >=1 ) {
            this.selectedCard = new Card(this.cards[0], true);
        } else {
            this.selectedCard = new Card(undefined, true);
        }
        this.selectedCard.bindOnButtonClick(() => this.sell())
        this.querySelector("#selectedCard").appendChild(this.selectedCard);
    }

    selectCard(card) {
        this.selectedCard.setCard(card);
    }

    async sell() {
        const priceSale = parseFloat(this.selectedCard.button.getValue());
        if(priceSale === undefined || isNaN(priceSale)) {
            this.errorSpan.innerHTML = "Invalid price"
        } else {
            const card = this.selectedCard.card;
            const transaction = {idCard: card.idInstance, priceSale}
            await SaleService.sellCard(transaction);
            this.render();
        }
    }
}

customElements.define('sell-view', SellView);