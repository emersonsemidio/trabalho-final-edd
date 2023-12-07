import matplotlib.pyplot as plt

# Sample data
num_loads = [1000, 5000, 10000, 200000, 300000]
load_times = [0.0051, 0.6597, 5.2499, 42.6174, 150.7350]

# Plotting the line graph
plt.plot(num_loads, load_times, marker='o', linestyle='-', color='blue')

# Adding labels and title
plt.title('Tempo médio de execução (minutos)')

# Display the graph
plt.show()