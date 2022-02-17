using AutoMapper;
using Microsoft.EntityFrameworkCore;
using Model;
using Model.DTOs;
using Persistence.Database;
using Service.Commons;
using Service.Extensions;
using System;
using System.Linq;
using System.Threading.Tasks;

namespace Service
{
    public interface IAlquilerService
    {
        Task<DataCollection<AlquilerDto>> GetAll(int page, int take);
        Task<AlquilerDto> GetById(int id);
        Task<AlquilerDto> Create(AlquilerCreateDto model);
        Task Update(int id, AlquilerUpdateDto model);
        Task Remove(int id);

        Task<DataCollection<AlquilerDto>> GetPorUsuario(string userId, int page, int take);
    }

    public class AlquilerService : IAlquilerService
    {
        private readonly ApplicationDbContext _context;
        private readonly IMapper _mapper;

        public AlquilerService(
            ApplicationDbContext context,
            IMapper mapper
        )
        {
            _context = context;
            _mapper = mapper;
        }

        public async Task<DataCollection<AlquilerDto>> GetAll(int page, int take)
        {
            return _mapper.Map<DataCollection<AlquilerDto>>(
                await _context.Alquileres.OrderByDescending(x => x.Id)
                              .AsQueryable()
                              .PagedAsync(page, take)
            );
        }

        public async Task<AlquilerDto> GetById(int id)
        {
            return _mapper.Map<AlquilerDto>(
                await _context.Alquileres.SingleAsync(x => x.Id == id)
            );
        }

        public async Task<AlquilerDto> Create(AlquilerCreateDto model)
        {
            var entry = new Alquiler
            {
                ParqueoId = model.ParqueoId,
                Fecha = model.Fecha,
                HoraId = model.HoraId,
                UserId = model.UserId,
                Price = model.Price
            };

            await _context.AddAsync(entry);
            await _context.SaveChangesAsync();

            return _mapper.Map<AlquilerDto>(entry);
        }

        public async Task Update(int id, AlquilerUpdateDto model)
        {
            var entry = await _context.Alquileres.SingleAsync(x => x.Id == id);

            entry.Estado= model.Estado;

            await _context.SaveChangesAsync();
        }

        public async Task Remove(int id)
        {
            _context.Remove(new Alquiler
            {
                Id = id
            });

            await _context.SaveChangesAsync();
        }

        //public async Task<DataCollection<AlquilerDto>> GetPorFecha(DateTime fecha)
        //{
        //    return _mapper.Map<DataCollection<AlquilerDto>>(
        //        await _context.Alquileres.Where(x => x.Fecha == fecha)
        //        .OrderBy(x => x.FechaCreado)
        //        .AsQueryable()
        //        .PagedAsync(1,100)
        //    );
        //}

        //public async Task<DataCollection<AlquilerDto>> GetPorFechaParqueo(DateTime fecha, int ParqueoId)
        //{
        //    return _mapper.Map<DataCollection<AlquilerDto>>(
        //        await _context.Alquileres.Where(x => x.Fecha == fecha && x.ParqueoId == ParqueoId)
        //        .OrderBy(x => x.FechaCreado)
        //        .AsQueryable()
        //        .PagedAsync(1, 100)
        //    );
        //}

        public async Task<DataCollection<AlquilerDto>> GetPorUsuario(string userId, int page, int take)
        {
            return _mapper.Map<DataCollection<AlquilerDto>>(
                await _context.Alquileres.Where(x => x.UserId == userId)
                .OrderByDescending(x => x.Fecha)
                .Include(x => x.Parqueo)
                .Include(x => x.Hora)
                .AsQueryable()
                .PagedAsync(page, take)
            );
        }
    }
}
