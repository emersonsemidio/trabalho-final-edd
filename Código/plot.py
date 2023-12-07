import matplotlib.pyplot as plt

num_loads = [10, 20, 30, 40, 50, 100, 200, 500, 1000, 5000, 10000, 20000, 30000]
load_times = [0.002, 0.001, 0.0, 0.001, 0.001, 0.002, 0.009, 0.058, 0.341, 37.79, 337.21, 2438,76, 11110]

plt.plot(num_loads, load_times, marker='o', linestyle='-', color='blue')

plt.title('Tempo médio de execução (segundos)')

plt.show()