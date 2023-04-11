from flask import Flask, jsonify
import requests
from bs4 import BeautifulSoup

app = Flask(__name__)


@app.route('/items', methods=['GET'])
def get_items():
    url = 'https://oldschool.runescape.wiki/w/RuneScape:Grand_Exchange_Market_Watch/Alchemy'
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'html.parser')
    items = []
    try:
        for row in soup.find_all('tr')[1:]:
            cols = row.find_all('td')
            items.append({
                'name': cols[0].text.strip(),
                'price': cols[1].text.strip()
            })
    except IndexError:
        # Handle the error here
        pass
    return jsonify(items)


if __name__ == '__main__':
    app.run(debug=True)
