
import matplotlib.pyplot as plt
import numpy as np




#Fernando
taslock_times = [6.324, 9.242, 11.032, 10.502, 9.015, 18.574, 12.798]
ttaslock_times = [4.33, 6.453, 8.589, 7.636, 12.239, 10.956, 6.806 ]
backofflock_times = [ 2.994, 3.808, 5.329, 7.636, 7.941, 9.665, 9.548]
clhlock_times = [9.141, 11.856, 14.423,  65.677, 80.883, 93.745, 166.19]
mcslock_times = [9.279, 10.355, 15.869, 62.72, 110.825,97.667,168.446]


#Gustavo
"""taslock_times = [2.899, 3.385, 6.114, 11.49, 15.692, 16.37, 16.876]
ttaslock_times = [2.493, 4.861, 7.772, 9.590, 14.297, 10.838, 11.471]
backofflock_times = [3.247, 3.885, 4.412, 4.862, 4.831, 5.267, 6.21]
clhlock_times = [6.280, 8.546, 12.113, 18.344, 66, 81.6, 137.4]
mcslock_times = [6.580, 6.691, 11.239, 16.524, 58.915,80.4,136.2]"""

#Luz

"""taslock_times = [8.306, 7.777, 21.897, 44.313, 56.648, 91.013, 68.88]
ttaslock_times = [8.486, 12.174, 17.511, 31.783, 67.418, 44.652, 73.772]
backofflock_times = [4.908, 5.367, 6.477, 11.365, 6.883, 7.986, 10.408]
clhlock_times = [20.446, 27.99, 31.738, 35.565, 119.78, 203.737, 326.793]
mcslock_times = [21.345, 24.117, 29.487, 30.837, 117.491, 200.855, 326.452]
"""
# Ejemplo de cantidades de hilos
num_threads_list = [2, 3, 7, 15, 21, 30, 50]

# Ancho de las barras
bar_width = 0.15

# Índices para las barras
index = np.arange(len(num_threads_list))

# Graficar los tiempos de ejecución con barras
plt.figure(figsize=(10, 6))

plt.bar(index, taslock_times, bar_width, label='TASLock')
plt.bar(index + bar_width, ttaslock_times, bar_width, label='TTASLock')
plt.bar(index + 2 * bar_width, backofflock_times, bar_width, label='BackoffLock')
plt.bar(index + 3 * bar_width, clhlock_times, bar_width, label='CLHLock')
plt.bar(index + 4 * bar_width, mcslock_times, bar_width, label='MCSLock')

plt.xlabel('Número de Hilos')
plt.ylabel('Tiempo Promedio (segundos)')
plt.title('Tiempos de Ejecución con Diferentes Tipos de Bloqueo')
plt.xticks(index + 2 * bar_width, num_threads_list)
plt.legend()
plt.grid()

plt.tight_layout()
plt.show()
