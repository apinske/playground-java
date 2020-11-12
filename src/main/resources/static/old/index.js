'use strict';

const apiClient = new Playground.ApiClient();
apiClient.basePath = '/playground-api';
const thingApi = new Playground.ThingApi(apiClient);

const camundaClient = new CamSDK.Client({
  mock: false,
  apiUri: '/rest'
});
const taskService = new camundaClient.resource('task');

class LinkList extends React.Component {
  render() {
    return (
      <ul className=" nav navbar-nav">
        {this.props.links.map(link =>
          <li className="nav-item" key={link.link}>
            <Link name={link.name} link={link.link} />
          </li>
        )}
      </ul>
    );
  }
}

class Link extends React.Component {
  render() {
    return <a className="nav-link" href={this.props.link}>{this.props.name}</a>;
  }
}

class ThingList extends React.Component {
  constructor(props) {
    super(props);
    this.state = { things: [], tasks: [], newThing: '' };
  }
  
  componentDidMount() {
    this.reload();
  }

  reload = () => {
    thingApi.getThings({}, (err, data, response) => {
      this.setState({
        things: data
      });
    });
    taskService.list({}, (err, results) => {
      this.setState({
        tasks: results._embedded.task
      });
    });
  }

  newThingChanged = (event) => {
    this.setState({ newThing: event.target.value });
  }

  saveNewThing = () => {
    thingApi.createThing({ name: this.state.newThing }, () => { 
      this.setState({ newThing: '' });
      this.reload(); 
    });
  }

  render() {
    return (
      <div>
        <ul>
          <li><i className="fas fa-inbox"></i></li>
          {this.state.things.map(thing =>
            <li key={thing.id}>
              <Thing thing={thing} />
            </li>
          )}
          {this.state.tasks.map(task =>
            <li key={task.id}>
              <Thing thing={task} />
            </li>
          )}
        </ul>
        <input value={this.state.newThing} onChange={this.newThingChanged}></input>
        <button onClick={this.saveNewThing}>Save</button>
      </div>
    );
  }
}

class Thing extends React.Component {
  constructor(props) {
    super(props);
    this.state = { thing: props.thing };
  }

  render() {
    return (
      <span data-id={this.state.thing.id} onClick={this.deleteThing}>{this.state.thing.name}</span>
    );
  }

  deleteThing = (event) => {
    thingApi.deleteThing(event.target.dataset.id);
  }
}

class Index extends React.Component {
  constructor(props) {
    super(props);
    this.sites = [
      { name: 'Data', link: '/playground-data' },
      { name: 'API', link: '/webjars/swagger-ui/index.html?url=/api.yaml' },
      { name: 'Camunda', link: '/app/cockpit/' },
      { name: 'Camunda API', link: '/rest/engine' },
      { name: 'DB', link: '/h2-console' }
    ];
  }

  render() {
    return [
      <nav key="1" className="navbar navbar-expand-sm fixed-top navbar-light bg-light">
        <a className="navbar-brand" href="/">Playground</a>
        <LinkList links={this.sites} />
      </nav>,
      <div key="2" className="container-fluid">
        <div className="row">
          <div className="col-2">
            <ThingList />
          </div>
        </div>
      </div>,
      <footer key="3" className="footer">
        <div className="container-fluid my-2">
          <span className="text-muted">Playground version - user</span>
        </div>
      </footer>
    ];
  }
}

ReactDOM.render(<Index />, document.querySelector('#index'));
