import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import store from "./store/store";
import { Provider } from "react-redux";
import { shallow } from "enzyme";

describe('Startup application tests', () => {
	test('Renders without crashing', () => {
		const div = document.createElement('div');
		ReactDOM.render(<Provider store={store}>
			<App />
		</Provider>, div);
		ReactDOM.unmountComponentAtNode(div);
	});

	test('it has correct header', () => {
		const wrapper = shallow(<App store={store}/>);
		const component = wrapper.dive();
		expect(component.find(".pageHeader").exists()).toEqual(true);
		expect(component.find(".pageHeader").text()).toEqual(" Grad Library App ")
	})
})
