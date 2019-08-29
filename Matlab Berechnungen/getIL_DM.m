function [IL] = getIL_DM(freq)
%UNTITLED2 Summary of this function goes here
%   Detailed explanation goes here
w = 2*pi*freq;

%Cy
Cy1 = 3.85e-9;
Ry1 = 246e-3;
Ly1 = 8e9;

%Cx
Rx1 = 47.4e-3;
Cx1 = 146.7e-9;
Lx1 = 15.2e-9;

% Drossel
Lr = 9.2e-6;
Rw = 11.3e-3;

% Querimpedanz Cy1
Zy1 = j*w*Ly1 + Ry1 + 1/(j*w*Cy1);
A1 = [1 0;
      1/Zy1 1];
% Querimpedanz Cx1
Zx1 = 0.5*j*w*Lx1 + 0.5*Rx1 + 1/(2*j*w*Cx1);
A2 = [1 0;
     1/Zx1 1];

% Längsimpedanz L0
Z0 = Rw + j*w*Lr;
A3 = [1 Z0;
      0 1];
  
% Querimpedanz Cx2
Zx2 = Zx1;
A4 = [1 0;
      1/Zx2 1];
% S21 Parameter ausrechnen
A = A1*A2*A3*A4;
Rb = 50;
s21 = 2/(A(1,1)+A(1,2)/Rb+A(2,1)*Rb+A(2,2));
IL = -20*log10(abs(s21));
end