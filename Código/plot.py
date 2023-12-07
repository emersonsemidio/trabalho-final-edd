import matplotlib.pyplot as plt

# Sample data
num_loads = [10, 20, 30, 40, 50, 100, 200, 500, 1000, 5000, 10000, 20000, 30000]
load_times = [0.002, 0.0, 0.0, 0.0, 0.0, 0.001, 0.005, 0.042, 0.319, 39.7, 343.05, 4364.82, 11110]

# Plotting the line graph
plt.plot(num_loads, load_times, marker='o', linestyle='-', color='blue')

# Adding labels and title
plt.title('Tempo médio de execução (segundos)')

# Display the graph
plt.show()