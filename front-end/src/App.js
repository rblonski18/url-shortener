import logo from './logo.svg';
import './App.css';
import ServiceBar from './components/ServiceBar'
import Redirect from './components/Redirect'
import {BrowserRouter, Switch, Route} from 'react-router-dom';

function App() {
  return (
    <div className="App">
        <BrowserRouter>
            <Switch>
                <Route exact path="/" component={ServiceBar}/>
                <Route exact path="/url/:urlID" component={Redirect}/>
            </Switch>
        </BrowserRouter>
    </div>
  );
}

export default App;
