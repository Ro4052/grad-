import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import store from "./store/store";
import { Provider } from "react-redux";


describe('Startup application tests', () => {
	test('Renders without crashing', () => {
		const div = document.createElement('div');
		ReactDOM.render(<Provider store={store}>
			<App />
		</Provider>, div);
		ReactDOM.unmountComponentAtNode(div);
	});

	test('dummy Test', () => {
		const testBook = {}
		const a = 5
		expect(a).toBe(5)
	})
})
