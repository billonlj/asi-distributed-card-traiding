import InputCustom from "./input.js"
import HTMLBindableElement from "./abstract/HTMLBindableElement.js";

class FullCard extends HTMLBindableElement {
    constructor(card, isSell = false) {
        super();

        if(card) {
            this.card = card;
            this.cardInstance = card.cardInstance !== undefined ? card.cardInstance : card; 
            //this.cardDescription = card.cardInstance !== undefined ? card.cardInstance : card.card; 
        }
        
        this.isSell = isSell;
    }

    bind(callback, name) {
        if (!callback) {
            return '';
        }
        window[name]=callback;
        return `window.${name}();`
    }

    bindOnButtonClick(callback) {
        this.callback = callback;
    }

    render() {
        if (this.card === undefined)
            return ;
        this.innerHTML = `
            <!------------------------------------------------------------------------->
            <!--    ----------------------------- CARD ----------------------------- -->
            <!------------------------------------------------------------------------->
            <div class="ui special cards">
                <div class="card">
            
                    <div class="content">
                        <div class="ui grid">
                            <div class="three column row">
                                <div class="column">
                                    <i class="heart outline icon"></i><span id="cardHPId">10</span>
                                </div>
                                <div class="column">
                                    <h5>Happy Tree Family</h5>
                                </div>
                                <div class="column">
                                    <span id="energyId">10</span> <i class="lightning icon"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="image imageCard">
            
                        <div class="blurring dimmable image">
                            <div class="ui inverted dimmer">
                                <div class="content">
                                    <div class="center">
                                        <div class="ui primary button">Add Friend</div>
                                    </div>
                                </div>
                            </div>
                            <div class="ui fluid image">
                                <a class="ui left corner label">
                                    ${this.cardInstance.card.nameCard}
                                </a>
                                <img id="cardImgId" class="ui centered image" src="${this.cardInstance.card.sourceUrlCard}">
                            </div>
                        </div>
                    </div>
                    <div class="content">
                        <div class="ui form tiny">
                            <div class="field">
                                <label id="cardNameId"></label>
                                <textarea id="cardDescriptionId" class="overflowHiden" readonly="" rows="2">${this.cardInstance.card.descriptionCard}
                                </textarea>
                            </div>
                        </div>
                    </div>
                    <div class="content">
                        <i class="heart outline icon"></i><span id="cardHPId"> HP ${this.cardInstance.hpInstance}</span>
                        <div class="right floated ">
                            <span id="cardEnergyId">Energy ${this.cardInstance.energyInstance}'</span>
                            <i class="lightning icon"></i>
            
                        </div>
                    </div>
                    <div class="content">
                        <span class="right floated">
                            <span id="cardAttackId"> Attack ${this.cardInstance.attackinstance}</span>
                            <i class=" wizard icon"></i>
                        </span>
                        <i class="protect icon"></i>
                        <span id="cardDefenceId">Defense ${this.cardInstance.defenceInstance}</span>
                    </div>
                    <div class="field">
                        <div class="ui left icon input" id="sellButton">
                            <i class="money icon"></i>
                        </div>
                    </div>
                    <div class="ui bottom attached button" onclick="${this.bind(() => this.onButtonClick(),"onCardButtonClick")}">
                        <i class="money icon"></i>
                        ${this.isSell ? "Sell" : "Buy"}
                    </div>
                </div>
            </div>
        `;

        const button = this.querySelector("#sellButton");
        if(this.isSell) {
            this.button = new InputCustom("sell", "number", "Price");
            button.appendChild(this.button);
        } else {
            button.classList.add("hidden");
        }
    }

    onButtonClick() {
        if(this.callback) this.callback()
    }

    setCard(newCard) {
        this.card = newCard;
        this.cardInstance = card.cardInstance;
		//this.cardDescription = newCard.cardInstance !== undefined ? newCard.cardInstance : newCard.card;
        this.render();
    }  
}

customElements.define('full-card', FullCard);

export default FullCard;    