const puppeteer = require("puppeteer");

let browser;
let page;

beforeAll(async () => {
  browser = await puppeteer.launch({
    headless: true,
    args: ["--no-sandbox"]
  });
  page = await browser.newPage();
  await page.goto(`http://${require("ip").address()}:3000/`, {
    waitUntil: "networkidle2"
  });
});

afterAll(async () => {
  await browser.close();
});

describe("Main page", () => {
  it('should display "Grad Library App" text on page', async () => {
    const text = await page.evaluate(() => document.body.textContent);
    expect(text).toContain("Grad Library App");
  });
});
