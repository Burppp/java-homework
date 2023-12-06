#include <iostream>
#include <stdlib.h>
#include <Eigen/Dense>
#include <time.h>
using namespace std;
int dimension = 0;
int** crossMatrix;
int* ans[2];
void printMatrix(int** matrix, int size)
{
	for (int i = 0; i < size; i++)
	{
		for (int j = 0; j < size; j++)
			cout << matrix[i][j] << ' ';
		cout << endl;
	}
}
void creatRandCross()
{
	int index = 0;
	for (int k = 0; k < dimension; k++)
	{
		int line = rand() % dimension;
		int row = rand() % dimension;
		ans[0][index] = line;
		ans[1][index++] = row;
		for (int i = 0; i < dimension; i++)
			crossMatrix[i][row] ^= 1;
		for (int j = 0; j < dimension; j++)
			crossMatrix[line][j] ^= 1;
		crossMatrix[line][row] ^= 1;
	}
	cout << "random matrix process:" << endl;
	for (int i = 0; i < dimension; i++)
		cout << "line:" << ans[0][i] << ' ' << "row:" << ans[1][i] << endl;
	for (int i = 0;i < dimension;i++)
	{
		for (int j = 0;j < dimension;j++)
			cout << crossMatrix[i][j] << ' ';
		cout << endl;
	}
}
void crossInit()
{
	crossMatrix = new int* [dimension];
	ans[0] = new int[dimension];
	ans[1] = new int[dimension];
	for (int i = 0; i < dimension; i++)
	{
		crossMatrix[i] = new int[dimension];
		for (int j = 0; j < dimension; j++)
			crossMatrix[i][j] = 0;
	}
	srand((unsigned int)time(NULL));
	creatRandCross();
}
bool checkWin()
{
	int status = crossMatrix[0][0];
	for (int i = 0; i < dimension; i++)
		for (int j = 0; j < dimension; j++)
			if (status != crossMatrix[i][j])
				return false;
	return true;
}
// Gaussian elimination for solving a linear system Ax = b
Eigen::VectorXd gaussianElimination(const Eigen::MatrixXd& A, const Eigen::VectorXd& b) {
	// Check if the system is square
	if (A.rows() != A.cols() || A.rows() != b.size()) {
		std::cerr << "Error: The system is not square or compatible." << std::endl;
		return Eigen::VectorXd();
	}
	
	// Augment the matrix A with the vector b
	Eigen::MatrixXd augmentedMatrix(A.rows(), A.cols() + 1);
	augmentedMatrix << A, b;
	
	// Perform Gaussian elimination
	for (int i = 0; i < augmentedMatrix.rows(); ++i) {
		// Partial pivoting: find the pivot row with the maximum absolute value in the current column
		int pivotRow = i;
		for (int j = i + 1; j < augmentedMatrix.rows(); ++j) {
			if (std::abs(augmentedMatrix(j, i)) > std::abs(augmentedMatrix(pivotRow, i))) {
				pivotRow = j;
			}
		}
		
		// Swap the current row with the pivot row
		augmentedMatrix.row(i).swap(augmentedMatrix.row(pivotRow));
		
		// Make the pivot element 1
		if (fmod(augmentedMatrix(i, i), 2.0f) != 0)
			augmentedMatrix.row(i) /= fmod(augmentedMatrix(i, i), 2.0f);
		
		// Eliminate other elements in the current column
		for (int j = 0; j < augmentedMatrix.rows(); ++j) {
			if (j != i) {
				augmentedMatrix.row(j) -= augmentedMatrix(j, i) * augmentedMatrix.row(i);
			}
		}
		
		for(int i = 0;i < augmentedMatrix.rows();i++)
			for (int j = 0;j < augmentedMatrix.cols();j++)
			{
				augmentedMatrix(i, j) = fmod(augmentedMatrix(i, j), -2);
				augmentedMatrix(i, j) += 2;
				augmentedMatrix(i, j) = fmod(augmentedMatrix(i, j), 2);
			}
		
	}
	
	// Extract the solution vector from the augmented matrix
	Eigen::VectorXd solution = augmentedMatrix.col(augmentedMatrix.cols() - 1);
	
	return solution;
}
void crossOptiomalSolution()
{
	int** CoefficientArray = new int* [dimension * dimension];
	int** templateMatrix = new int* [dimension];
	for (int i = 0; i < dimension * dimension; i++)
		CoefficientArray[i] = new int[dimension * dimension];
	for (int i = 0; i < dimension; i++)
	{
		templateMatrix[i] = new int[dimension];
		for (int j = 0; j < dimension; j++)
			templateMatrix[i][j] = 0;
	}
	int index = 0, index_i = 0, index_j = 0;
	for (int line = 0; line < dimension * dimension; line++)
	{
		index_i = index / dimension;
		index_j = index % dimension;
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++)
			{
				if (i == index_i || j == index_j)
					templateMatrix[i][j] = 1;
				else
					templateMatrix[i][j] = 0;
			}
		for (int i = 0; i < dimension; i++)
			memcpy(CoefficientArray[line] + dimension * i, templateMatrix[i], dimension * sizeof(int));
		index++;
	}
	Eigen::MatrixXd CoefficientMatrix(dimension * dimension, dimension * dimension);
	for (int i = 0;i < dimension * dimension;i++)
		for (int j = 0;j < dimension * dimension;j++)
			CoefficientMatrix(i, j) = CoefficientArray[i][j];
	Eigen::VectorXd targetStatus(dimension * dimension, 1);
	int init = 0;
	for (int i = 0;i < dimension;i++)
		for (int j = 0;j < dimension;j++)
			targetStatus(init++) = crossMatrix[i][j];
	Eigen::VectorXd ansVector = gaussianElimination(CoefficientMatrix, targetStatus);
	ansVector.array() += 2;
	for (int i = 0;i < ansVector.size();i++)
		ansVector[i] = fmod(ansVector[i], 2.0f);
	cout << "Optiomal Solution:" << endl;
	for (int i = 0;i < ansVector.size();i++)
	{
		cout << ansVector(i) << ' ';
		if (i % dimension == dimension - 1)
			cout << endl;
	}
}
void crossReverse()
{
	while (1)
	{
		if (checkWin())
			break;
		crossOptiomalSolution();
		int line = 0, row = 0;
		cout << "Select the rows and columns to flip:" << endl;
		cin >> line >> row;
		line--;
		row--;
		while (line < 0 || row < 0 || line >= dimension || row >= dimension)
		{
			cerr << "Array index out of range exception, retry:";
			cin >> line >> row;
			line--;
			row--;
		}
		for (int i = 0; i < dimension; i++)
			crossMatrix[i][row] ^= 1;
		for (int j = 0; j < dimension; j++)
			crossMatrix[line][j] ^= 1;
		crossMatrix[line][row] ^= 1;
		cout << "Current matrix:" << endl;
		printMatrix(crossMatrix, dimension);
	}
	cout << "Win!" << endl;
}
int main()
{
	cout << "Please enter the row size of the cross:" << endl;
	cin >> dimension;
	crossInit();
	crossReverse();
	return 0;
}

