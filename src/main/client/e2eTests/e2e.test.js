import styles from "../src/dashBoard/DashBoard.module.css";
const puppeteer = require("puppeteer");

let browser;
let page;

beforeAll(async () => {
  browser = await puppeteer.launch({
    headless: false
    //args: ['--disable-dev-shm-usage'],
  });
  page = await browser.newPage();
  await page.goto("https://bristol-library-dev.herokuapp.com/");
  await page.waitForSelector(styles.pageHeader);
});

afterAll(async () => {
  await browser.close();
});

describe("Main page", () => {
  it('should display "Grad Library App" text on page', async () => {
    const text = await page.evaluate(() => document.body.textContent);
    expect(text).toContain("Grad Library App");
  });
  //it('shows a list of books', async () => {
  // await page.$$('.bookList')
  //})
});

// describe('Adding a book',() => {
//     it('the add book field is visible', async () => {
//         await page.$$('.addBook');
//     })
// })
