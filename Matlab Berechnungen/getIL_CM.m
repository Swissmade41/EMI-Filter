function [IL] = getIL_CM(freq)
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here

w = 2*pi*freq;

%Drossel
Rw = 11.3e-3;
Lr = 9.2e-6;
Cp = 6.46e-12;
Rp = 7.92e3;
L0 = 0.5e-3;

% Cy
Ry1 = 246e-3;
Cy1 = 3.85e-9;
Ly1 = 8e-9;

% Querimpedanz Cy
Zy = 0.5*j*w*Ly1 + 0.5*Ry1 + 1/(2*j*w*Cy1);
A1 = [1 0;
      1/Zy 1];

% Längsimpedanz L0 
Z0 = par(1/(2*j*w*Cp),par(0.5*Rp,0.5*j*w*L0)) + 0.5*Rw + j*w*0.5*Lr;
A2 = [1, Z0;
      0, 1];

% Berechnung S21
A = A1*A2;
Rb = 50;
s21 = 2/(A(1,1)+A(1,2)/Rb+A(2,1)*Rb+A(2,2));
IL = -20*log10(abs(s21));
end

