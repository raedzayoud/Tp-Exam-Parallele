# Simulation d’un Système de Livraison de Colis

## Description
Ce projet simule la gestion d’un système de livraison de colis en intégrant des concepts de **programmation parallèle** (threads, sémaphores et moniteurs) et une interface utilisateur interactive basée sur **Swing** pour visualiser les statuts des colis.

## Fonctionnalités principales

### 1. Enregistrement des colis
- Plusieurs utilisateurs (**threads**) peuvent enregistrer des colis simultanément.
- Synchronisation des accès grâce à des **sémaphores** et **moniteurs**.
- Vérification des doublons (par **ID**) lors de l’enregistrement.

### 2. Gestion de la livraison
- Simulation des étapes de livraison :
  - **En attente**
  - **En transit**
  - **Livré**
- Mise à jour automatique des statuts dans l’interface.

### 3. Affichage en temps réel
- Utilisation de **Swing** pour visualiser les colis et leurs statuts.
- Mise en évidence des statuts par des **codes couleur** :
  - **Orange** : En attente
  - **Violet** : En transit
  - **Vert** : Livré

## Technologies utilisées
- **Langage** : Java
- **Interface graphique** : Swing (**Dark Mode** amélioré)
- **Programmation parallèle** :
  - **Threads** : Gestion des utilisateurs concurrents.
  - **Sémaphores** : Contrôle d’accès aux ressources partagées.
  - **Moniteurs** : Garantissent l’exclusion mutuelle.

## Classes principales

### 1. Colis
- Représente un colis avec des attributs tels que :
  - **ID**
  - **Destination**
  - **Statut**

### 2. GestionnaireColis
- Gère les opérations telles que :
  - **Enregistrement**
  - **Mise à jour des statuts**
  - **Livraison**

### 3. ThreadEnregistrement
- Simule un utilisateur enregistrant un colis.
- Vérifie les doublons.

### 4. ThreadLivraison
- Simule les étapes de livraison et met à jour le statut des colis.

### 5. InterfaceSwing
- Crée l’interface graphique et permet l’interaction utilisateur.

## Synchronisation
- **Threads** : Simulent des utilisateurs en interaction concurrente avec le système.
- **Sémaphores** : Limitent le nombre de threads pouvant accéder à une ressource donnée.
- **Moniteurs** : Empêchent les accès simultanés conflictuels aux données partagées.

## Photos

![Capture d’écran 2024-12-10 124227](https://github.com/user-attachments/assets/7fedf125-643e-45ae-95d2-06180728ccd6)
![Capture d’écran 2024-12-10 124321](https://github.com/user-attachments/assets/edc6009a-329a-4a26-86b1-c196c0a8b376)
![Capture d’écran 2024-12-10 124300](https://github.com/user-attachments/assets/ea7186cf-0239-4cca-9be4-fad6e6acf23d)
![Capture d’écran 2024-12-10 124455](https://github.com/user-attachments/assets/7635182b-11bd-4954-b9f3-0d4f9a25aff9)
