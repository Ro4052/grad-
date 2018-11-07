const puppeteer = require('puppeteer');

let browser; 
let page;

describe('Google', () => {
    beforeAll (async () => {
        browser = await puppeteer.launch({
            headless: false
        });
        page = await browser.newPage();
    })

    beforeEach(async () => { 
        await page.goto('https://google.com'); 
    })

    it('should display "google" text on page', async () => {
        const text = await page.evaluate(() => document.body.textContent)
        expect(text).toContain('google')
      })

    afterEach(async () => { 
        await browser.close()
    })
})
