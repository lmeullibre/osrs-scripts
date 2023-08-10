# Automated High Alchemy with Intelligent Item Selection for RuneScape

This project aims to build a comprehensive bot for the popular MMORPG game, RuneScape, focused on the High Alchemy (high-level magic) spell. The bot automates the process of buying and selling items, leveraging the in-game Grand Exchange, and then alchemizing them for profit.

Key Features:

* Intelligent Item Selection: The bot uses a microservice to fetch the most profitable items for high alchemy. It maintains this list of items in a queue data structure, ensuring the most profitable item is always prioritized.

* Handling Item Unavailability: The bot is smart enough to move to the next profitable item in the queue if the current item is unavailable in the Grand Exchange at the desired price, ensuring there is no idle time.

* Max Coin Spend Threshold: The bot keeps track of how much money it has spent to avoid exceeding a user-defined maximum coin limit.

* Offer Tracking: The bot makes use of the RuneScape's Grand Exchange API to track the status of placed offers, ensuring efficient execution and maximizing profits.

* Error Handling: The bot incorporates several error-handling measures to ensure smooth and consistent operation. This includes stopping when there are no items left to alch or if the max coin limit has been reached.

* Efficiency: With automated buying, selling, and alchemizing, this bot significantly reduces the manual labor required to turn a profit using the High Alchemy spell.

For educational purposes only
