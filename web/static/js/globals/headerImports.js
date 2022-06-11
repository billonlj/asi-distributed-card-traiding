const JSScripts = [
  'lib/axios.min.js',
  'lib/eventSource.js',
  'lib/jquery-3.3.1.min.js',
  'lib/Semantic-UI-CSS-master/semantic.js'
]

const CSSLinks = [
  'lib/Semantic-UI-CSS-master/semantic.min.css',
  'css/custom.css'
]

class HeaderImports extends HTMLElement {
  constructor() {
    super();
    this.loaded = {};
  }

  addScript(url) {
    if (!this.loaded[url]) {
      var s = document.createElement('script');
      s.src = url;
      document.head.appendChild(s);
      this.loaded[url] = true;
    }
  }

  addLink(url) {
    if (!this.loaded[url]) {
      var s = document.createElement('link');
      s.href = url;
      s.rel = 'stylesheet';
      s.type = 'text/css';
      document.head.appendChild(s);
      this.loaded[url] = true;
    }
  }

  connectedCallback() {
    CSSLinks.forEach(link => this.addLink(link));
    JSScripts.forEach(script => this.addScript(script));
  }
}

customElements.define('header-imports', HeaderImports);