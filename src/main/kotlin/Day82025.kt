package inlamningsuppgift2.joakim

import java.io.File

class Day82025(inputFile: String) {
    private val points: List<Point> = File(inputFile)
        .readLines()
        .filter { it.isNotBlank() }
        .map { line ->
            val (x, y, z) = line.split(",").map(String::trim).map(String::toLong)
            Point(x, y, z)
        }

    fun solve() {
        val edges = buildSortedEdges()
        val uf = UnionFind(points.size)
        var connections = 0

        edges.forEachIndexed { t, edge ->
            if (t == 1000) {
                val componentSizes = IntArray(points.size)
                for (i in points.indices) {
                    componentSizes[uf.find(i)]++
                }

                val top3 = componentSizes
                    .filter { it > 0 }
                    .sortedDescending()
                    .take(3)

                println("${top3[0]} * ${top3[1]} * ${top3[2]}")
                println(top3[0].toLong() * top3[1] * top3[2])
            }

            if (uf.union(edge.i, edge.j)) {
                connections++
                if (connections == points.size - 1) {
                    println("${points[edge.i].x} * ${points[edge.j].x}")
                    println(points[edge.i].x * points[edge.j].x)
                }
            }
        }
    }

    fun exportVisualisationData(outputDir: String) {
        val dir = File(outputDir)
        dir.mkdirs()

        val edges = buildSortedEdges()

        exportPoints(File(dir, "points.csv"))
        exportEdges(File(dir, "early_edges.csv"), acceptedEdges(edges, 50))
        exportComponents(
            File(dir, "clusters_300.csv"),
            edges,
            processedEdges = 300,
            topK = 10
        )
        exportComponents(
            File(dir, "top3_1000.csv"),
            edges,
            processedEdges = 1000,
            topK = 3
        )
        exportEdges(
            File(dir, "final_mst_edges.csv"),
            acceptedEdges(edges, points.size - 1)
        )
    }

    private fun buildSortedEdges(): List<Edge> {
        return buildList {
            for (i in points.indices) {
                val p1 = points[i]
                for (j in 0 until i) {
                    val p2 = points[j]
                    val dx = p1.x - p2.x
                    val dy = p1.y - p2.y
                    val dz = p1.z - p2.z
                    val distance = dx * dx + dy * dy + dz * dz
                    add(Edge(distance, i, j))
                }
            }
        }.sortedBy(Edge::distance)
    }

    private fun acceptedEdges(
        sortedEdges: List<Edge>,
        maxAccepted: Int
    ): List<Edge> {
        val uf = UnionFind(points.size)
        val accepted = ArrayList<Edge>()

        for (edge in sortedEdges) {
            if (uf.union(edge.i, edge.j)) {
                accepted.add(edge)
                if (accepted.size == maxAccepted) {
                    break
                }
            }
        }

        return accepted
    }

    private fun exportPoints(file: File) {
        file.printWriter().use { out ->
            out.println("x,y,z")
            for (p in points) {
                out.println("${p.x},${p.y},${p.z}")
            }
        }
    }

    private fun exportEdges(file: File, edges: List<Edge>) {
        file.printWriter().use { out ->
            out.println("x1,y1,z1,x2,y2,z2")
            for (edge in edges) {
                val a = points[edge.i]
                val b = points[edge.j]
                out.println("${a.x},${a.y},${a.z},${b.x},${b.y},${b.z}")
            }
        }
    }

    private fun exportComponents(
        file: File,
        sortedEdges: List<Edge>,
        processedEdges: Int,
        topK: Int
    ) {
        val uf = UnionFind(points.size)

        repeat(minOf(processedEdges, sortedEdges.size)) { t ->
            val edge = sortedEdges[t]
            uf.union(edge.i, edge.j)
        }

        val roots = IntArray(points.size)
        val sizes = mutableMapOf<Int, Int>()

        for (i in points.indices) {
            val root = uf.find(i)
            roots[i] = root
            sizes[root] = (sizes[root] ?: 0) + 1
        }

        val topRoots = sizes.entries
            .sortedByDescending { it.value }
            .take(topK)
            .mapIndexed { index, entry ->
                entry.key to "obj${index + 1}"
            }
            .toMap()

        file.printWriter().use { out ->
            out.println("x,y,z,group,size")

            points.forEachIndexed { i, p ->
                val root = roots[i]
                val group = topRoots[root] ?: "other"
                val size = sizes[root] ?: 0
                out.println("${p.x},${p.y},${p.z},$group,$size")
            }
        }
    }

    private data class Point(val x: Long, val y: Long, val z: Long)

    private data class Edge(val distance: Long, val i: Int, val j: Int)

    private class UnionFind(size: Int) {
        private val parent = IntArray(size) { it }

        fun find(x: Int): Int {
            if (parent[x] != x) {
                parent[x] = find(parent[x])
            }
            return parent[x]
        }

        fun union(a: Int, b: Int): Boolean {
            val rootA = find(a)
            val rootB = find(b)

            if (rootA == rootB) return false

            parent[rootA] = rootB
            return true
        }
    }
}