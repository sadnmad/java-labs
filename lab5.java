import numpy as np
import matplotlib.pyplot as plt
from sklearn.neural_network import MLPRegressor
from sklearn.metrics import mean_squared_error

# Step 1: Define the target function
def target_function(x):
    return 2 * np.sin(2 * x)

# Step 2: Generate training and testing data
np.random.seed(42)  # For reproducibility

x_train = np.linspace(0, 1, 100).reshape(-1, 1)  # Training inputs
y_train = target_function(x_train)               # Training targets

x_test = np.linspace(0, 1, 100).reshape(-1, 1)   # Testing inputs
y_test = target_function(x_test)                # Testing targets

# Add noise to the training data
y_train_noisy = y_train + 0.1 * np.random.normal(size=y_train.shape)

# Step 3: Create and train neural networks with varying hidden layer sizes
hidden_layer_configurations = [(1,), (3,), (5,), (7,), (10,)]
results = {}

for config in hidden_layer_configurations:
    # Initialize the MLP regressor
    model = MLPRegressor(hidden_layer_sizes=config, activation='tanh', max_iter=1000, random_state=42)

    # Train the model
    model.fit(x_train, y_train_noisy.ravel())

    # Predict on test data
    y_pred = model.predict(x_test)

    # Compute mean squared error
    mse = mean_squared_error(y_test, y_pred)

    # Store results
    results[config] = {'model': model, 'mse': mse, 'predictions': y_pred}

    print(f"Model with hidden layer {config} - MSE: {mse:.4f}")

# Step 4: Visualize the results
plt.figure(figsize=(15, 10))

for i, (config, result) in enumerate(results.items(), 1):
    plt.subplot(2, 3, i)
    plt.scatter(x_train, y_train_noisy, label="Training Data", color="blue", alpha=0.5)
    plt.plot(x_test, y_test, label="True Function", color="green")
    plt.plot(x_test, result['predictions'], label=f"Prediction ({config})", color="red")
    plt.title(f"Hidden Layer: {config}, MSE: {result['mse']:.4f}")
    plt.legend()

plt.tight_layout()
plt.show()

# Step 5: Evaluate the best model
best_config = min(results, key=lambda x: results[x]['mse'])
print(f"Best configuration: {best_config} with MSE: {results[best_config]['mse']:.4f}")
