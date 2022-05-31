import { getGlobalComponent } from "../../modules/app.js";
import HTMLBindableElement from "./HTMLBindableElement.js";

class HTMLView extends HTMLBindableElement {
    constructor() {
      super();

      this.router = getGlobalComponent("router");
    }
  }
  
export default HTMLView;