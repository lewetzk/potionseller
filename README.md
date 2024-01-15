# Potionseller 2.0 - Furhat RPG Potion Seller

## Project Overview

This project, Potionseller 2.0, is a fantasy RPG-style potion seller implemented using the Furhat API and written in Kotlin. The application introduces a social robot, Furhat, equipped with expressions, agent detection, and head tracking features to create an immersive and engaging interaction with users.

## Project Description

### Features

1. **User-Directed Orders:**
   - Potionseller 2.0 is designed to be more flexible in handling user-directed orders. It won't proactively ask about potion effects or strengths but rather waits for the user's request.

2. **User Queries:**
   - Users can inquire about available ingredients and seek general help at any point in the conversation. Example queries include asking about available options, potion effects, and strength levels.

3. **Clarification Counter:**
   - In the order confirmation intent, a clarification counter is implemented. This counter keeps track of user-initiated changes or affirmations. If a user attempts to modify the order more than twice, Potionseller 2.0 will display impatience and throw the user out of the potion shop.

4. **Expressive Gestures:**
   - Furhat, the social robot, is programmed to exhibit appropriate gestures during the interaction. For instance, it starts with a friendly smile but turns angry when throwing the user out of the potion shop.

### Usage

1. **Ordering Process:**
   - Users can initiate the ordering process by specifying their desired potion and its strength. Potionseller 2.0 will then seek clarification if necessary.

2. **Information Queries:**
   - Users can ask for information about available ingredients, potion effects, and strength options at any point in the conversation.

3. **Impatience Mechanism:**
   - The application monitors user modifications, and if the user attempts to change the order more than twice, Potionseller 2.0 will display impatience and terminate the interaction.

### Furhat API and Kotlin

The application leverages the Furhat API, a powerful tool for creating social robots with natural language processing and expressive capabilities. The implementation is written in Kotlin, a versatile and concise programming language that integrates seamlessly with the Furhat platform.

## Getting Started

To run Potionseller 2.0, follow these steps:

1. **Furhat Setup:**
   - Ensure you have the Furhat SDK installed and configured.

2. **Kotlin Environment:**
   - Set up a Kotlin environment and ensure the necessary dependencies are installed.

3. **API Integration:**
   - Integrate the Furhat API into the Kotlin project and configure the connection to the Furhat robot.

4. **Run the Application:**
   - Execute the Kotlin application to launch Potionseller 2.0 on the Furhat platform.

## Future Enhancements

1. **Expanded Potion Options:**
   - Introduce a wider range of potions and effects to enhance user choices.

2. **Dynamic Gestures:**
   - Implement more dynamic and context-aware gestures for a richer user experience.

3. **Customization Options:**
   - Allow users to customize their orders with additional details, such as color or flavor preferences.

4. **Multi-User Support:**
   - Enable simultaneous interactions with multiple users for a more immersive group experience.

## Contributors

- **Lea Wetzke**
  - BSc in Computerlinguistik
  - Contact: [lea.wetzke@gmail.com](mailto:lea.wetzke@gmail.com)
    
---

Feel free to contribute, report issues, or suggest improvements!
