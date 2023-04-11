import json
import requests

# Get JSON from URL
url = "https://raw.githubusercontent.com/osrsbox/osrsbox-db/master/docs/items-complete.json"
response = requests.get(url)
data = response.json()

# Create new list with only ID and name
new_data = []
for item in data:
    if data[item]["tradeable_on_ge"] == True and data[item]["members"] == False:
        new_data.append({"id": data[item]["id"], "name": data[item]["name"]})

# Save new list to a file
with open("raw.json", "w") as outfile:
    json.dump(new_data, outfile)
